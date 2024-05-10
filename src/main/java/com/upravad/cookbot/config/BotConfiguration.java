package com.upravad.cookbot.config;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.annotation.Configuration;
import com.upravad.cookbot.exception.BaseException;
import com.upravad.cookbot.core.BotCore;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;

/**
 * Configuration class for the bot.
 *
 * @see TelegramBotsApi
 * @see BotCore
 * @author daktah
 * @version 1.0.0
 */
@Slf4j
@Getter
@Configuration
@RequiredArgsConstructor
public class BotConfiguration {

  private final BotCore botCore;
  private boolean isRunning;

  /**
   * The method registers a {@link BotCore} in Telegram
   * by passing the {@code username} and {@code token} in the parameters.
   *
   * @see ContextRefreshedEvent
   * @see DefaultBotSession
   * @author daktah
   */
  @PostConstruct
  public void init() {
    try {
      TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
      BotSession session = botsApi.registerBot(botCore);
      isRunning = session.isRunning();
      log.info("\uD83D\uDCF6 \033[1;94m{} registered: \033[0;97m{}\033[0m",
          botCore.getBotUsername(), isRunning);

    } catch (TelegramApiException e) {
      throw new BaseException(e, "Failed to register bot");
    }
  }
}
