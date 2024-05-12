package com.upravad.cookbot.core;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Main functions of the bot.
 *
 * @author daktah
 */
@Getter
@AllArgsConstructor
public enum MainOptions implements Options {

  START("Регистрирует пользователя в базе данных"),
  HELP("Выводит список доступных команд"),
  STICKER("Команда-маркер для логирования отправки стикеров"),
  ERROR("Команда-маркер для логирования обработки ошибок"),
  CREATE("Создает рецепт");

  private final String description;

  @Override
  public String getCommand() {
    return "/" + this.name().toLowerCase();
  }

}
