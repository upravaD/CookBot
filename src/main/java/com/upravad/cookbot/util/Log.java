package com.upravad.cookbot.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Log {

  OPTION("\033[1;93mt{} \033[0;97m{}\033[0m"),
  LOG_ERROR("\033[1;91m{} \033[0;97m{}\033[0m");

  private final String message;
}
