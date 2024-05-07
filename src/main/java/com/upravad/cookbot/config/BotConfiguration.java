package com.upravad.cookbot.config;

import java.security.SecureRandom;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

@Configuration
@PropertySource("classpath:application.yml")
public class BotConfiguration extends AbilityBot {

//  @Value("${telegram.bot.name}")
//  private String botUsername;
//
//  @Value("${telegram.bot.token}")
//  private String botToken;

  @Autowired
  protected BotConfiguration(
      @Value("${telegram.bot.token}") String botToken,
      @Value("${telegram.bot.name}") String botUsername) {
    super(botToken, botUsername);
  }

  @Override
  public long creatorId() {
    return new SecureRandom().nextLong(0, Long.MAX_VALUE);
  }

}
