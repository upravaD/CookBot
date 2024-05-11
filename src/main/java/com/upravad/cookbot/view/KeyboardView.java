package com.upravad.cookbot.view;

import com.upravad.cookbot.core.Options;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Slf4j
@Component
@RequiredArgsConstructor
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
    Arrays.stream(Options.values()).toList().stream()
        .filter(option -> !option.name().equals(Options.ERROR.name()))
        .filter(option -> !option.name().equals(Options.STICKER.name()))
        .filter(option -> !option.name().equals(Options.BREAKFAST.name()))
        .forEach(option -> row.add(Options.getCommand(option)));
    return row;
  }

}
