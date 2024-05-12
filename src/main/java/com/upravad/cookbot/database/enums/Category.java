package com.upravad.cookbot.database.enums;

import static java.util.Arrays.stream;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import com.upravad.cookbot.core.Options;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

/**
 * Dishes categories.
 */
@Getter
@AllArgsConstructor
public enum Category implements Options {

  BREAKFAST("Завтрак"),
  FIRST("Первое блюдо"),
  SECOND("Второе блюдо"),
  SALAD("Салат"),
  SNACKS("Закуски"),
  DRINKS("Напитки");

  private final String description;

  public static List<BotCommand> getCommands() {
    return stream(Category.values())
        .map(category -> new BotCommand(category.getCommand(), category.getDescription()))
        .toList();
  }

  @Override
  public String getCommand() {
    return "/" + this.name().toLowerCase();
  }

}
