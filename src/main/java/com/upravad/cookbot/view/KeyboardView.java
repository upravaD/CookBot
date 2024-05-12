package com.upravad.cookbot.view;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.springframework.stereotype.Component;
import com.upravad.cookbot.core.MainOptions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class KeyboardView implements View {

  public ReplyKeyboard getKeyboardMarkup() {
    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    keyboardMarkup.setKeyboard(getRowList());
    keyboardMarkup.setResizeKeyboard(true);
    return keyboardMarkup;
  }

  private List<KeyboardRow> getRowList() {
    List<KeyboardRow> keyboardRows = new ArrayList<>();
    keyboardRows.add(getOptionsKeyboardRow());
    return keyboardRows;
  }

  private KeyboardRow getOptionsKeyboardRow() {
    KeyboardRow row = new KeyboardRow();
    Arrays.stream(MainOptions.values()).toList().stream()
        .filter(option -> !option.name().equals(MainOptions.ERROR.name()))
        .filter(option -> !option.name().equals(MainOptions.STICKER.name()))
        .forEach(option -> row.add(option.getCommand()));
    return row;
  }

}
