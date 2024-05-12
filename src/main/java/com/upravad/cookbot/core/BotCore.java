package com.upravad.cookbot.core;

import static com.upravad.cookbot.database.enums.Category.getCommands;
import static com.upravad.cookbot.exception.ExceptionMessage.NOT_EXECUTED;
import static com.upravad.cookbot.util.RegexUtil.UUID;

import com.upravad.cookbot.database.enums.Category;
import com.upravad.cookbot.exception.BaseException;
import com.upravad.cookbot.service.impl.MainOptionService;
import com.upravad.cookbot.service.impl.RecipeService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * {@code BotCore} the main processor of the bot and this class is responsible for processing incoming updates from
 * {@code Telegram}.
 *
 * @author daktah
 */
@Slf4j
@Component
public class BotCore extends TelegramLongPollingBot {

  @Value("${telegram.cookbot.name}")
  private String username;
  private final MainOptionService mainOptionService;
  private final RecipeService recipeService;

  public BotCore(@Value("${telegram.cookbot.token}") String token,
      RecipeService recipeService,
      MainOptionService mainOptionService) {
    super(token);
    this.recipeService = recipeService;
    this.mainOptionService = mainOptionService;
    commit(new SetMyCommands());
  }

  /**
   * Return username of this bot.
   */
  @Override
  public String getBotUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * This method is called when receiving updates via GetUpdates method.
   *
   * @param update Update received
   */
  @Override
  public void onUpdateReceived(Update update) {

    if (update.hasMessage() && update.getMessage().hasText()) {

      Set<Options> options = getOptionsSet();

      options.stream()
          .filter(MainOptions.class::isInstance)
          .map(MainOptions.class::cast)
          .filter(option -> update.getMessage().getText().equals(option.getCommand()))
          .findFirst()
          .ifPresent(option -> {
            switch (option) {

              // Main commands
              case START -> commit(mainOptionService.sendLogo(update), mainOptionService.start(update));
              case HELP -> commit(mainOptionService.help(update));

              // Recipes commands
              case CREATE -> commit(recipeService.create(update));

              default -> log.info(update.getMessage().getText().toLowerCase());
            }
          });

      options.stream()
          .filter(Category.class::isInstance)
          .map(Category.class::cast)
          .filter(option -> update.getMessage().getText().equals(option.getCommand()))
          .findFirst()
          .ifPresent(option -> commit(recipeService.sendCategories(update, option)));
    }

    if (update.hasCallbackQuery() && update.getCallbackQuery().getData().matches(UUID.getRegex())) {
      commit(recipeService.sendRecipe(update.getCallbackQuery()));
    }

    if (update.hasMessage() && update.getMessage().hasSticker()) {
      commit(mainOptionService.sendSticker(update));
    }
  }

  private Set<Options> getOptionsSet() {
    Set<Options> options = new HashSet<>();
    options.addAll(Arrays.asList(MainOptions.values()));
    options.addAll(Arrays.asList(Category.values()));
    return options;
  }

  /**
   * The method validates the delivery type and executes it.
   *
   * @param validables from any Sender
   */
  private void commit(Validable... validables) {
    for (Validable validable : validables) {
      log.info("\uD83E\uDD66\033[0;92m Success \033[0;97m{}\033[0m", validable.getClass().getSimpleName());
      try {
        if (validable instanceof SendMessage message) {
          execute(message);
          log.info("\033[0;93m✉ answer text ⤵\n\033[0;97m{}\033[0m", message.getText());

        }
        if (validable instanceof SendPhoto photo) {
          execute(photo);
          log.info("\033[0;93m✉ photo caption ⤵\n\033[0;97m{}\033[0m", photo.getCaption());

        }
        if (validable instanceof SetMyCommands command) {
          command.setCommands(getCommands());
          command.setScope(new BotCommandScopeDefault());
          command.setLanguageCode("ru");
          execute(command);
          log.info("\033[0;93m✉ commands list ⤵\n\033[0;97m{}\033[0m", command.getCommands());
        }
      } catch (TelegramApiException e) {
        throw new BaseException(e, validable.getClass().getSimpleName() + NOT_EXECUTED.getMessage());
      }
    }
  }

  //TODO: Пролистование кнопок
  private void buttonTap(Long id, String callbackId, String data, int msgId) {

    EditMessageText newTxt = EditMessageText.builder()
        .chatId(id.toString())
        .messageId(msgId)
        .text("").build();

    EditMessageReplyMarkup newKb = EditMessageReplyMarkup.builder()
        .chatId(id.toString())
        .messageId(msgId).build();

    if (data.equals("next")) {
      newTxt.setText("MENU 2");
      newKb.setReplyMarkup(InlineKeyboardMarkup.builder()
          .keyboardRow(List.of(InlineKeyboardButton.builder().text("2").build()))
          .build());
    } else if (data.equals("back")) {
      newTxt.setText("MENU 1");
      newKb.setReplyMarkup(InlineKeyboardMarkup.builder()
          .keyboardRow(List.of(InlineKeyboardButton.builder().text("1").build()))
          .build());
    }

    AnswerCallbackQuery close = AnswerCallbackQuery.builder()
        .callbackQueryId(callbackId).build();

    commit(close);
    commit(newTxt);
    commit(newKb);
  }
}
