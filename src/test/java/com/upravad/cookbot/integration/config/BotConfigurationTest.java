package com.upravad.cookbot.integration.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import com.upravad.cookbot.exception.BaseException;
import com.upravad.cookbot.config.BotConfiguration;
import com.upravad.cookbot.config.CookBotTest;
import com.upravad.cookbot.core.BotCore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CookBotTest
class BotConfigurationTest {

  @Autowired
  private BotConfiguration botConfig;

  @Autowired
  private BotCore core;

  @Test
  @DisplayName("Successful registration")
  void testInit_successfulRegistration()  {
    core.setUsername("upravaDCookBot");
    botConfig.init();

    assertTrue(log.isInfoEnabled());
    assertTrue(log.isErrorEnabled());
    assertTrue(botConfig.isRunning());
    assertNotNull(botConfig.getBotCore());
  }

  @Test
  @DisplayName("Registration with null username")
  void testInit_unsuccessfulRegistration() {
    core.setUsername(null);
    assertThrows(BaseException.class, () -> botConfig.init());
  }

}