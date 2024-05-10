package com.upravad.cookbot.database.enums;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;
import java.util.List;

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

  public static List<BotCommand> getCommands() {
    return Arrays.stream(Category.values())
        .map(category -> new BotCommand("/" + category.name().toLowerCase(), category.getS()))
        .toList();
  }
}
