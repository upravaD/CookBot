package com.upravad.cookbot.core;

import static java.util.stream.Stream.of;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Main functions of the bot.
 *
 * @author daktah
 */
@Getter
@AllArgsConstructor
public enum Options {

  // Main options

  /**
   * User registration.
   */
  START("/start", "Регистрирует пользователя в базе данных"),

  /**
   * List of all bot commands.
   */
  HELP("/help", "Выводит список доступных команд"),

  /**
   * Stickers.
   */
  STICKER("/sticker", "Команда-маркер для логирования отправки стикеров"),


  // Exceptions

  /**
   * Error message.
   */
  ERROR("/error", "Команда-маркер для логирования обработки ошибок"),


  // Recipe options

  /**
   * Create a recipe.
   */
  CREATE("/create", "Создает рецепт"),

  /**
   * Get a recipe.
   */
  GET("/get", "Получает рецепт");

  private final String name;
  private final String description;

  /**
   * Validates the option.
   *
   * @param optionValue String value.
   * @return Validated option.
   */
  public static Options validate(String optionValue) {
    return of(Options.values())
        .filter(options -> options.name().equals(optionValue.substring(1).toUpperCase()))
        .findFirst()
        .orElse(ERROR);
  }

}
