package com.upravad.cookbot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

  // General exception messages
  NOT_FOUND(" not found"),
  NOT_EXECUTED(" not executed"),
  EXCEPTION_MESSAGE("И че это такое??? ахаха жми -> /help чтобы понять себя"),

  // Photo exception messages
  PHOTO_SIMPLE("Simple photo not downloaded"),
  PHOTO_CAPTION("Photo with caption not downloaded");

  private final String message;

}
