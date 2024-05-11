package com.upravad.cookbot.service.impl;

import static com.upravad.cookbot.database.enums.Category.getCommand;
import static com.upravad.cookbot.database.enums.Category.BREAKFAST;
import static com.upravad.cookbot.core.Options.getCommand;
import static com.upravad.cookbot.core.Options.CREATE;
import static com.upravad.cookbot.core.Options.GET;
import static com.upravad.cookbot.util.Log.OPTION;

import com.upravad.cookbot.core.senders.MessageSender;
import com.upravad.cookbot.core.senders.PhotoSender;
import com.upravad.cookbot.database.dto.DishDto;
import com.upravad.cookbot.database.mapper.DishesMapper;
import com.upravad.cookbot.service.interfaces.BotService;
import com.upravad.cookbot.view.ButtonsView;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService implements BotService {

  private final DishesService dishesService;
  private final DishesMapper dishesMapper;
  private final PhotoSender photoSender;
  private final MessageSender messageSender;
  private final ButtonsView buttonsView;

  public SendMessage sendLogo(Update update) {
    return messageSender.sendMessage(update, "\uD83C\uDF54");
  }

  public SendMessage sendBreakfast(Update update) {
    log.info(OPTION.getMessage(), getCommand(BREAKFAST), update.getMessage().getFrom());
    List<DishDto> dishes = dishesService.readAllByCategory(BREAKFAST);
    return messageSender.sendMessage(update, BREAKFAST.getDescription(), buttonsView.getButtons(dishes));
  }

  public SendMessage create(Update update) {
    log.info(OPTION.getMessage(), getCommand(CREATE), update.getMessage().getFrom());
    dishesService.create(dishesMapper.toDto(dishesService.getDish()));
    return messageSender.sendMessage(update, "Рецепт создан");
  }

  public SendPhoto get(CallbackQuery callback) {
    log.info(OPTION.getMessage(), getCommand(GET), callback.getFrom());
    DishDto dto = dishesService.read(UUID.fromString(callback.getData()));
    return photoSender.sendPhoto(callback.getMessage().getChatId(),
        dto.getImageUrl(),
        dto.getName() + "\n" + "-".repeat(30) + "\n" +
        dto.description() + "-".repeat(30) + "\n" +
        dto.getRecipe());
  }

  public SendPhoto get(Update update) {
    log.info(OPTION.getMessage(), getCommand(GET), update.getMessage().getFrom());
    DishDto dto = dishesService.read(UUID.fromString("295a4cb3-e496-4cc8-84f4-d59a5c6ea058"));
    return photoSender.sendPhoto(update.getMessage().getChatId(),
        dto.getImageUrl(),
        dto.getName() + "\n" + "-".repeat(30) + "\n" +
        dto.description() + "-".repeat(30) + "\n" +
        dto.getRecipe());
  }
}
