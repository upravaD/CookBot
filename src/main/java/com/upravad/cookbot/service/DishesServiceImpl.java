package com.upravad.cookbot.service;

import static com.upravad.cookbot.util.Color.GREEN_BRIGHT;
import static com.upravad.cookbot.util.Color.RESET;

import com.upravad.cookbot.database.dto.DishDto;
import com.upravad.cookbot.database.mapper.DishesMapper;
import com.upravad.cookbot.database.mapper.IngredientMapper;
import com.upravad.cookbot.database.model.Dish;
import com.upravad.cookbot.database.model.Ingredient;
import com.upravad.cookbot.database.repository.DishesRepository;
import com.upravad.cookbot.database.repository.IngredientRepository;
import com.upravad.cookbot.exception.BaseException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DishesServiceImpl implements DishesService {

  private final DishesRepository dishesRepository;
  private final IngredientRepository ingredientRepository;
  private final DishesMapper dishesMapper;
  private final IngredientMapper ingredientMapper;

  private static final String BRACE = GREEN_BRIGHT + "{}" + RESET;

  @Override
  @Transactional
  public void create(DishDto dishDto) {
    Dish dish = dishesRepository.save(dishesMapper.toEntity(dishDto));
    log.info("Dish created: {}", dish);
    dishDto.getIngredients()
        .forEach(ingredientDto -> {
          Ingredient ingredient = ingredientMapper.toEntity(ingredientDto);
          ingredient.setDish(dish);
          ingredientRepository.save(ingredient);
        });
    log.info("Ingredients added to dish");
  }

  public DishDto readByName(String name) {
    log.info("Reading dish by name: {}", name);
    return dishesMapper.toDto(dishesRepository
        .findByName(name)
        .orElseThrow(() -> new BaseException("Dish not found")));
  }

  @Override
  @Transactional
  public DishDto read(UUID dishesId) {
    log.info("Reading dish: {}", dishesId);
    Dish dish = dishesRepository.findById(dishesId)
        .orElseThrow(() -> new BaseException("Dish not found"));
    log.info("Set ingredients");
    dish.setIngredients(ingredientRepository.findAllByDish(dish));
    return dishesMapper.toDto(dish);
  }

  @Override
  public void update(DishDto dishDto) {
    log.info("Update dish: {}", dishDto.getName());
    dishesRepository.save(dishesMapper.toEntity(dishDto));
  }

  @Override
  public void delete(UUID dishesId) {
    log.info("Delete dish: {}", dishesId);
    dishesRepository
        .findById(dishesId)
        .ifPresent(dishesRepository::delete);
  }
}
