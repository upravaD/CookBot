package com.upravad.cookbot.config;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import com.upravad.cookbot.core.BotCore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuration class for the bot.
 *
 * @see TelegramBotsApi
 * @see BotCore
 * @author daktah
 * @version 1.0.0
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class BotConfiguration {

  private final BotCore botCore;

  /**
   * The method registers a {@link BotCore} in Telegram
   * by passing the {@code username} and {@code token} in the parameters.
   *
   * @see ContextRefreshedEvent
   * @see DefaultBotSession
   * @author daktah
   */
  @EventListener({ContextRefreshedEvent.class})
  public void init() {
    try {
      TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
      botsApi.registerBot(botCore);
      log.info("\uD83D\uDCF6 \033[1;94m{} registered\033[0m", botCore.getBotUsername());
    } catch (TelegramApiException e) {
      log.error("\033[1;91m{}: \033[0;97m{}\033[0m", e.getClass().getSimpleName(), e.getMessage());
    }
  }
}
