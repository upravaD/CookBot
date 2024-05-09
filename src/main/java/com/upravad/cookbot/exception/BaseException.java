package com.upravad.cookbot.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Parent exception class throughout the application.
 *
 * @see RuntimeException
 * @author daktah
 * @version 1.0.0
 */
@Slf4j
public class BaseException extends RuntimeException {

  /**
   * Constructs a new runtime exception
   * with the specified detail message.
   *
   * @param message detail message
   */
  public BaseException(String message) {
    super("\033[1;91m" + message + "\033[0m");
  }

  /**
   * Constructs a new runtime exception
   * with the specified detail message and exception type.
   *
   * @param throwable exception type
   * @param message detail message
   */
  public BaseException(Throwable throwable, String message) {
    super(message);
    log.error("\033[1;91m" + throwable.getClass().getSimpleName() +
              ": \033[0;97m{}\nStackTrace: {}" + "\033[0m",
              throwable.getMessage(),
              throwable.getStackTrace());
  }

}
