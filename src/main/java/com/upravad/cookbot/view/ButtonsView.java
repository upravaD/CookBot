package com.upravad.cookbot.view;

import com.upravad.cookbot.database.dto.DishDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Component
public class ButtonsView implements View {

  public InlineKeyboardMarkup getButtons(List<DishDto> dishes) {
    var markup = new InlineKeyboardMarkup();
    markup.setKeyboard(getRows(dishes));
    return markup;
  }

  private List<List<InlineKeyboardButton>> getRows(List<DishDto> dishes) {
    List<List<InlineKeyboardButton>> rows = new ArrayList<>();
    dishes.forEach(dish -> rows.add(getRow(dish)));
    return rows;
  }

  private List<InlineKeyboardButton> getRow(DishDto dish) {
    return List.of(InlineKeyboardButton.builder()
        .text(dish.getName())
        .callbackData(dish.getId().toString())
        .build());
  }

}
