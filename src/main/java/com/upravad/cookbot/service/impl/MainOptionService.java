package com.upravad.cookbot.service.impl;

import static com.upravad.cookbot.exception.ExceptionMessage.EXCEPTION_MESSAGE;
import static com.upravad.cookbot.core.Options.getCommand;
import static com.upravad.cookbot.core.Options.STICKER;
import static com.upravad.cookbot.core.Options.ERROR;
import static com.upravad.cookbot.core.Options.START;
import static com.upravad.cookbot.core.Options.HELP;
import static com.upravad.cookbot.util.Log.LOG_ERROR;
import static com.upravad.cookbot.util.Log.OPTION;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.upravad.cookbot.service.interfaces.BotService;
import com.upravad.cookbot.core.senders.MessageSender;
import com.upravad.cookbot.view.KeyboardView;
import com.upravad.cookbot.core.Options;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainOptionService implements BotService {
  private final MessageSender messageSender;

  private final KeyboardView keyboardView;

  public SendMessage start(Update update) {
    log.info(OPTION.getMessage(), getCommand(START), update.getMessage().getFrom());
    return messageSender.sendMessage(update,
        "Регистрация завершена",
        keyboardView.getKeyboardMarkup());
  }

  public SendMessage help(Update update) {
    log.info(OPTION.getMessage(), getCommand(HELP), update.getMessage().getFrom());
    StringBuilder sb = new StringBuilder();
    AtomicInteger i = new AtomicInteger(1);

    Arrays.stream(Options.values())
        .filter(option -> !option.name().equals(ERROR.name()) && !option.name().equals(STICKER.name()))
        .toList()
        .forEach(option -> sb
            .append(i.getAndIncrement())
            .append(". ")
            .append(getCommand(option))
            .append(" - ")
            .append(option.getDescription())
            .append("\n"));

    return messageSender.sendMessage(update, sb.toString());
  }
  public SendMessage sendSticker(Update update) {
    log.info(OPTION.getMessage(), getCommand(STICKER), update.getMessage().getFrom());
    return messageSender.sendMessage(update, update.getMessage().getSticker().getEmoji());
  }

  public SendMessage sendError(Update update) {
    log.error(LOG_ERROR.getMessage(), update.getMessage().getText().toLowerCase(), update.getMessage().getFrom());
    return messageSender.errorMessage(update, EXCEPTION_MESSAGE.getMessage());
  }
}
