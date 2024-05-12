package com.upravad.cookbot.service.impl;

import static com.upravad.cookbot.core.MainOptions.CREATE;
import static com.upravad.cookbot.util.LogUtil.OPTION;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.upravad.cookbot.service.interfaces.BotService;
import com.upravad.cookbot.database.mapper.DishesMapper;
import com.upravad.cookbot.core.senders.MessageSender;
import com.upravad.cookbot.core.senders.PhotoSender;
import com.upravad.cookbot.database.enums.Category;
import com.upravad.cookbot.database.dto.DishDto;
import com.upravad.cookbot.view.ButtonsView;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.UUID;

/**
 * Recipe service of the Cookbot.
 *
 * @author daktah
 * @version 1.0
 * @see SendMessage
 * @see SendPhoto
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService implements BotService {

  private final DishesService dishesService;
  private final DishesMapper dishesMapper;
  private final PhotoSender photoSender;
  private final MessageSender messageSender;
  private final ButtonsView buttonsView;

  /**
   * Prepare a message with all the dishes of the category.
   *
   * @param update from telegram
   * @param category of dishes
   * @return a SendMessage
   * @see DishesService
   * @see ButtonsView
   * @see Update
   */
  public SendMessage sendCategories(Update update, Category category) {
    log.info(OPTION.getLog(), category.getCommand(), update.getMessage().getFrom());
    List<DishDto> dishes = dishesService.readAllByCategory(category);
    return messageSender.sendMessage(
        update,
        category.getDescription(),
        buttonsView.getDishesButtons(dishes)
    );
  }

  /**
   * Prepare a photo with the recipe of the dish in the caption
   * and inline buttons in a message.
   *
   * @param callback from telegram
   * @return a SendPhoto
   * @see CallbackQuery
   * @see DishesService
   * @see ButtonsView
   */
  public SendPhoto sendRecipe(CallbackQuery callback) {
    log.info(OPTION.getLog(), "/" + callback.getClass().getSimpleName().toLowerCase().substring(0, 8), callback.getFrom());
    DishDto dto = dishesService.read(UUID.fromString(callback.getData()));
    return photoSender.sendPhoto(
        callback.getMessage().getChatId(),
        dto.getImageUrl(),
        dto.getRecipe(),
        buttonsView.getPhotoButtons(dto)
    );
  }

  //TODO Создание рецептов
  public SendMessage create(Update update) {
    log.info(OPTION.getLog(), CREATE.getCommand(), update.getMessage().getFrom());
    dishesService.create(dishesMapper.toDto(dishesService.getDish()));
    return messageSender.sendMessage(
        update,
        "Рецепт создан"
    );
  }

}
