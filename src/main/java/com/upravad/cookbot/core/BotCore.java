package com.upravad.cookbot.core;

import static com.upravad.cookbot.database.enums.Category.BREAKFAST;

import com.upravad.cookbot.database.dto.DishDto;
import com.upravad.cookbot.database.mapper.DishesMapper;
import com.upravad.cookbot.database.model.Dish;
import com.upravad.cookbot.database.model.Ingredient;
import com.upravad.cookbot.exception.BaseException;
import com.upravad.cookbot.service.DishesService;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class BotCore extends TelegramLongPollingBot {

  private final DishesService dishesService;
  private final DishesMapper dishesMapper;

  @Value("${telegram.bot.token}")
  private String token;
  @Value("${telegram.bot.name}")
  private String username;

  /**
   * Return username of this bot
   */
  public String getBotUsername() {
    return username;
  }

  /**
   * Return token of this bot
   */
  @Override
  public String getBotToken() {
    return token;
  }

  /**
   * This method is called when receiving updates via GetUpdates method
   *
   * @param update Update received
   */
  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      String messageText = update.getMessage().getText().toLowerCase();
      Long chatId = update.getMessage().getChatId();

      switch (messageText) {
        case "/start" -> sendMessage(chatId, "Hello " + update.getMessage().getFrom().getUserName());
        case "/help" -> sendMessage(chatId, "1. /create - создать рецепт \n2. /get - получить рецепт");
        case "/create" -> dishesService.create(dishesMapper.toDto(getDish()));
        case "/get" -> {
          DishDto dto = dishesService.read(UUID.fromString("295a4cb3-e496-4cc8-84f4-d59a5c6ea058"));
          log.info("{} get recipe: {}", update.getMessage().getFrom().getUserName(), dto.getName());
          sendPhoto(chatId, dto.getImageUrl());
          sendMessage(chatId, dto.getName());
          sendMessage(chatId, dto.description());
          sendMessage(chatId, dto.getRecipe());
        }
        default -> sendMessage(chatId, "I don't know what to do with this message");
      }
    }
  }

  private void sendMessage(Long chatId, String text) {
    SendMessage message = new SendMessage();
    message.setChatId(chatId);
    message.setText(text);

    try {
      execute(message);
    } catch (TelegramApiException e) {
      throw new BaseException(e.getMessage());
    }
  }

  private void sendPhoto(Long chatId, String url) {
    SendPhoto photo = new SendPhoto();
    try(InputStream stream = new URL(url).openStream()) {
      photo.setChatId(chatId);
      photo.setPhoto(new InputFile(stream, url));
      execute(photo);
    } catch (IOException | TelegramApiException e) {
      log.error(e.getMessage());
      throw new BaseException("Photo not downloaded");
    }
  }

  private Dish getDish() {
    return Dish.builder()
        .name("Овсянка с яблоками")
        .category(BREAKFAST)
        .ingredients(getIngredients())
        .recipe("Положите овсянку, сахар и соль в кастрюлю, залейте водой и варите до готовности. Очищенные и нарезанные яблоки добавьте в кашу за пять минут до окончания варки. Подавайте горячую кашу с маслом и сливками.")
        .imageUrl("https://wellnessplay.com.br/wp-content/uploads/2019/09/Depositphotos_38611825_xl-2015.jpg")
        .price(getPrice(getIngredients()))
        .build();
  }

  private Set<Ingredient> getIngredients() {
    return Set.of(
        getIngredient("Вода", 600, 7.89),
        getIngredient("Сахар", 65, 3.47),
        getIngredient("Соль", 2, 0.04),
        getIngredient("Овсянка", 200, 10.99),
        getIngredient("Яблоки", 600, 7.89),
        getIngredient("Масло сливочное", 50, 34.37),
        getIngredient("Сливки", 200, 40.48)
    );
  }

  private Ingredient getIngredient(String name, Integer weight, Double cost) {
    return Ingredient.builder()
        .name(name)
        .weight(weight)
        .cost(cost)
        .build();
  }

  private Double getPrice(Set<Ingredient> ingredients) {
    return ingredients.stream()
        .mapToDouble(Ingredient::getCost)
        .sum();
  }
}
