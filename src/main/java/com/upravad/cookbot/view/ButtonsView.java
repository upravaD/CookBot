package com.upravad.cookbot.view;

import static com.upravad.cookbot.util.Messages.SBER_URL;
import static com.upravad.cookbot.util.Messages.ORDER;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import com.upravad.cookbot.database.dto.IngredientDto;
import org.springframework.stereotype.Component;
import com.upravad.cookbot.database.dto.DishDto;
import java.util.ArrayList;
import java.util.List;

@Component
public class ButtonsView implements View {

  /**
   * Category options.
   *
   * @param dishes from service
   * @return prepared InlineKeyboardMarkup for category buttons
   */
  public InlineKeyboardMarkup getDishesButtons(List<DishDto> dishes) {
    var markup = new InlineKeyboardMarkup();
    markup.setKeyboard(getDishesRows(dishes));
    return markup;
  }

  private List<List<InlineKeyboardButton>> getDishesRows(List<DishDto> dishes) {
    var rows = new ArrayList<List<InlineKeyboardButton>>();
    dishes.forEach(dish -> rows.add(getDishesRow(dish)));
    return rows;
  }

  private List<InlineKeyboardButton> getDishesRow(DishDto dish) {
    return List.of(InlineKeyboardButton.builder()
        .text(dish.getName())
        .callbackData(dish.getId().toString())
        .build());
  }


  /**
   * Recipe options.
   *
   * @param dish from CallbackQuery
   * @return prepared InlineKeyboardMarkup for recipe buttons
   */
  public InlineKeyboardMarkup getPhotoButtons(DishDto dish) {
    var markup = new InlineKeyboardMarkup();
    markup.setKeyboard(getPhotoRows(dish));
    return markup;
  }

  private List<List<InlineKeyboardButton>> getPhotoRows(DishDto dish) {
    List<List<InlineKeyboardButton>> rows = new ArrayList<>();
    rows.add(getHeaderRow("Ингредиенты: ", "ingredients"));
    dish.getIngredients().forEach(ingredient -> rows.add(getIngredientRow(ingredient)));
    rows.add(getCostRow(dish));
    rows.add(getOrderRow());
    return rows;
  }

  private List<InlineKeyboardButton> getHeaderRow(String text, String data) {
    return List.of(InlineKeyboardButton.builder()
        .text(text)
        .callbackData(data)
        .build());
  }

  private List<InlineKeyboardButton> getOrderRow() {
    return List.of(InlineKeyboardButton.builder()
        .text(ORDER.getMessage())
        .url(SBER_URL.getMessage())
        .build());
  }

  private List<InlineKeyboardButton> getCostRow(DishDto dish) {
    return List.of(InlineKeyboardButton.builder()
        .text("Стоимость: " + dish.getPrice() + " ₽")
        .callbackData(dish.getId().toString())
        .build());
  }

  private List<InlineKeyboardButton> getIngredientRow(IngredientDto ingredient) {
    return List.of(
        InlineKeyboardButton.builder()
            .text(ingredient.getName())
            .callbackData(ingredient.getId().toString())
            .build(),
        InlineKeyboardButton.builder()
            .text(ingredient.getWeight() + "г")
            .callbackData(ingredient.getId().toString())
            .build()
    );
  }
}
