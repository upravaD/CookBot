package com.upravad.cookbot.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages {

  SBER_URL("https://sbermarket.ru"),
  ORDER("Заказать");

  private final String message;

}
