package com.upravad.cookbot.core;

import static com.upravad.cookbot.exception.ExceptionMessage.NOT_EXECUTED;
import static com.upravad.cookbot.exception.ExceptionMessage.ERROR;
import static com.upravad.cookbot.core.Options.CREATE;
import static com.upravad.cookbot.core.Options.START;
import static com.upravad.cookbot.core.Options.HELP;
import static com.upravad.cookbot.core.Options.GET;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.interfaces.Validable;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.upravad.cookbot.service.impl.DishesServiceImpl;
import com.upravad.cookbot.database.mapper.DishesMapper;
import com.upravad.cookbot.database.dto.DishDto;
import com.upravad.cookbot.core.senders.MessageSender;
import com.upravad.cookbot.core.senders.PhotoSender;
import com.upravad.cookbot.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import java.util.UUID;

/**
 * {@code BotCore} the main processor of the bot and
 * this class is responsible for processing incoming updates from {@code Telegram}.
 *
 * @author daktah
 */
@Slf4j
@Component
public class BotCore extends TelegramLongPollingBot {

  private final DishesServiceImpl dishesService;
  private final DishesMapper dishesMapper;
  @Value("${telegram.cookbot.name}")
  private String username;
  private final PhotoSender photoSender;
  private final MessageSender messageSender;
  private static final String OPTION_LOG = "\033[1;93mt{} \033[0;97m{}\033[0m";

  public BotCore(@Value("${telegram.cookbot.token}") String token,
      DishesServiceImpl dishesService,
      DishesMapper dishesMapper,
      MessageSender messageSender,
      PhotoSender photoSender) {
    super(token);
    this.dishesService = dishesService;
    this.dishesMapper = dishesMapper;
    this.messageSender = messageSender;
    this.photoSender = photoSender;
  }

  /**
   * Return username of this bot.
   */
  @Override
  public String getBotUsername() {
    return username;
  }

  /**
   * This method is called when receiving updates via GetUpdates method.
   *
   * @param update Update received
   */
  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      String messageText = update.getMessage().getText().toLowerCase();

      switch (Options.validate(messageText)) {
        case START -> {
          log.info(OPTION_LOG, START.getName(), update.getMessage().getFrom());
          commit(messageSender.sendMessage(update, "Hello " + update.getMessage().getFrom().getUserName()));
        }
        case HELP -> {
          log.info(OPTION_LOG, HELP.getName(), update.getMessage().getFrom());
          commit(messageSender.sendMessage(update, "1. /create - создать рецепт \n2. /get - получить рецепт"));
        }
        case CREATE -> {
          log.info(OPTION_LOG, CREATE.getName(), update.getMessage().getFrom());
          dishesService.create(dishesMapper.toDto(dishesService.getDish()));
          commit(messageSender.sendMessage(update, "Рецепт создан"));
          commit(messageSender.sendMessage(update, "\uD83C\uDF54"));
        }
        case GET -> {
          log.info(OPTION_LOG, GET.getName(), update.getMessage().getFrom());
          DishDto dto = dishesService.read(UUID.fromString("295a4cb3-e496-4cc8-84f4-d59a5c6ea058"));
          commit(photoSender.sendPhoto(update,
              dto.getImageUrl(),
              dto.getName() + "\n" + "-".repeat(30) + "\n" +
              dto.description() + "-".repeat(30) + "\n" +
              dto.getRecipe()));
        }
        default -> {
          log.error(OPTION_LOG, messageText, update.getMessage().getFrom());
          commit(messageSender.errorMessage(update, ERROR.getMessage()));
        }
      }
    }
  }

  /**
   * The method validates the delivery type and executes it.
   *
   * @param validable from any Sender
   */
  private void commit(Validable validable) {
    log.info("\uD83E\uDD66\033[0;92m Success \033[0;97m{}\033[0m", validable.getClass().getSimpleName());
    try {
      if (validable instanceof SendMessage message) {
        execute(message);
        log.info("\033[0;93m✉ text ⤵\n\033[0;97m{}\033[0m", message.getText());
      }
      if (validable instanceof SendPhoto photo) {
        execute(photo);
        log.info("\033[0;93m✉ caption ⤵\n\033[0;97m{}\033[0m", photo.getCaption());
      }
    } catch (TelegramApiException e) {
      throw new BaseException(e, validable.getClass().getSimpleName() + NOT_EXECUTED);
    }
  }

}
