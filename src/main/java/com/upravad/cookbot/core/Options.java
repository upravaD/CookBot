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
  START("/start"),

  /**
   * List of all bot commands.
   */
  HELP("/help"),

  /**
   * Error message.
   */
  ERROR("???"),


  // Recipe options

  /**
   * Create a recipe.
   */
  CREATE("/create"),

  /**
   * Get a recipe.
   */
  GET("/get");

  private final String name;

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
