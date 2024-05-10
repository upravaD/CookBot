package com.upravad.cookbot.core;

import static com.upravad.cookbot.core.Options.validate;
import static com.upravad.cookbot.exception.ExceptionMessage.NOT_EXECUTED;

import com.upravad.cookbot.exception.BaseException;
import com.upravad.cookbot.service.impl.RecipeService;
import com.upravad.cookbot.service.impl.MainOptionService;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * {@code BotCore} the main processor of the bot and
 * this class is responsible for processing incoming updates from {@code Telegram}.
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

      switch (validate(update.getMessage().getText().toLowerCase())) {
        case START -> commit(mainOptionService.start(update));
        case HELP -> commit(mainOptionService.help(update));
        case CREATE -> commit(recipeService.create(update), recipeService.sendLogo(update));
        case GET -> commit(recipeService.get(update));
        default -> commit(mainOptionService.sendError(update));
      }
    }

    if (update.getMessage().hasSticker()) commit(mainOptionService.sendSticker(update));
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
      } catch (TelegramApiException e) {
        throw new BaseException(e, validable.getClass().getSimpleName() + NOT_EXECUTED);
      }
    }
  }

}
