package com.upravad.cookbot.database.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Dishes categories.
 */
@Getter
@AllArgsConstructor
public enum Category {

  BREAKFAST("Завтрак"),
  FIRST("Первое блюдо"),
  SECOND("Второе блюдо"),
  SALAD("Салат"),
  SNACKS("Закуски"),
  DRINKS("Напитки");

  private final String s;

}
