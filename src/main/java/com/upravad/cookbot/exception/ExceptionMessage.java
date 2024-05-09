package com.upravad.cookbot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

  // Photo exception messages
  PHOTO_SIMPLE("Simple photo not downloaded"),
  PHOTO_CAPTION("Photo with caption not downloaded");

  private final String message;

}
