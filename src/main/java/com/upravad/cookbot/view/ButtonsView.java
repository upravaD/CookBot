package com.upravad.cookbot.view;

import static com.upravad.cookbot.util.Messages.SBER_URL;
import static com.upravad.cookbot.util.Messages.ORDER;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
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
  public InlineKeyboardMarkup getPhotoButtons(DishDto dish, Integer multiplier) {
    var markup = new InlineKeyboardMarkup();
    markup.setKeyboard(getPhotoRows(dish, multiplier));
    return markup;
  }

  public InlineKeyboardMarkup getWeightButtons(String data) {
    var markup = new InlineKeyboardMarkup();
    markup.setKeyboard(getWeightRows(data));
    return markup;
  }

  public EditMessageReplyMarkup getPhotoButtonsTap(DishDto dish, String chatId, Integer messageId, Integer multiplier) {
    var markup = new EditMessageReplyMarkup();
    markup.setChatId(chatId);
    markup.setMessageId(messageId);
    markup.setReplyMarkup(getPhotoButtons(dish, multiplier));
    return markup;
  }

  private List<List<InlineKeyboardButton>> getPhotoRows(DishDto dish, Integer multiplier) {
    List<List<InlineKeyboardButton>> rows = new ArrayList<>();
    rows.add(getRow("назад", "back/" + multiplier +"/" + dish.getId().toString()));
    rows.add(getRow("Ингредиенты: ", "ingredients"));
    dish.getIngredients().forEach(ingredient -> rows.add(getIngredientRow(ingredient, multiplier)));
    rows.add(getCostRow(dish, multiplier));
    rows.add(getOrderRow());
    return rows;
  }

  private List<List<InlineKeyboardButton>> getWeightRows(String data) {
    List<List<InlineKeyboardButton>> rows = new ArrayList<>();
    rows.add(getRow("Выберите объем готового блюда: ", "weight"));
    rows.add(getRow("1.5 литра", "w1" + data));
    rows.add(getRow("4 литра", "w2" + data));
    rows.add(getRow("6.5 литров", "w3" + data));
    return rows;
  }

  private List<InlineKeyboardButton> getRow(String text, String data) {
    return List.of(InlineKeyboardButton.builder()
        .text(text)
        .callbackData(data)
        .build());
  }

  private List<InlineKeyboardButton> getOrderRow() {
    return List.of(InlineKeyboardButton.builder()
        .text(ORDER.getMessage())
        .callbackData("sber")
        .url(SBER_URL.getMessage())
        .build());
  }

  private List<InlineKeyboardButton> getCostRow(DishDto dish, Integer multiplier) {
    return List.of(InlineKeyboardButton.builder()
        .text("Стоимость: " + dish.getPrice() * multiplier + " ₽")
        .callbackData("price/" + dish.getId().toString())
        .build());
  }

  private List<InlineKeyboardButton> getIngredientRow(IngredientDto ingredient, Integer multiplier) {
    return List.of(
        InlineKeyboardButton.builder()
            .text(ingredient.getName())
            .callbackData("ingn/" + ingredient.getId().toString())
            .build(),
        InlineKeyboardButton.builder()
            .text(ingredient.getWeight() * multiplier + "г")
            .callbackData("ingw/" + ingredient.getId().toString())
            .build()
    );
  }
}
