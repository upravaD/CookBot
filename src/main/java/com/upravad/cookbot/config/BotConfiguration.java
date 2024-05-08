package com.upravad.cookbot.config;

import com.upravad.cookbot.core.BotCore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BotConfiguration {
  private final BotCore botCore;

  @EventListener({ContextRefreshedEvent.class})
  public void init() throws TelegramApiException {
    TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
    try {
      botsApi.registerBot(botCore);
      log.info("Bot started");
    } catch (TelegramApiException e) {
      log.error(e.getMessage());
    }
  }
}
