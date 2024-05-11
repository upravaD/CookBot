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
  START("Регистрирует пользователя в базе данных"),

  /**
   * List of all bot commands.
   */
  HELP("Выводит список доступных команд"),

  /**
   * Stickers.
   */
  STICKER("Команда-маркер для логирования отправки стикеров"),


  // Exceptions

  /**
   * Error message.
   */
  ERROR("Команда-маркер для логирования обработки ошибок"),


  // Category options
  /**
   * Create a recipe.
   */
  BREAKFAST("Открывает клавиши для завтраков"),

  // Recipe options

  /**
   * Create a recipe.
   */
  CREATE("Создает рецепт"),

  /**
   * Get a recipe.
   */
  GET("Получает рецепт");

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

  public static String getCommand(Options option) {
    return "/" + option.name().toLowerCase();
  }
}
