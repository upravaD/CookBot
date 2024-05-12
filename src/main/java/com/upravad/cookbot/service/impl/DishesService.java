package com.upravad.cookbot.service.impl;

import static com.upravad.cookbot.database.enums.Category.BREAKFAST;
import static com.upravad.cookbot.exception.ExceptionMessage.NOT_FOUND;

import com.upravad.cookbot.database.enums.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.upravad.cookbot.service.interfaces.PostgresService;
import com.upravad.cookbot.database.repository.IngredientRepository;
import com.upravad.cookbot.database.repository.DishesRepository;
import com.upravad.cookbot.database.mapper.IngredientMapper;
import com.upravad.cookbot.database.mapper.DishesMapper;
import com.upravad.cookbot.database.model.Ingredient;
import com.upravad.cookbot.database.model.Dish;
import com.upravad.cookbot.database.dto.DishDto;
import com.upravad.cookbot.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.UUID;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class DishesService implements PostgresService<UUID, DishDto> {

  private final IngredientRepository ingredientRepository;
  private final DishesRepository dishesRepository;
  private final IngredientMapper ingredientMapper;
  private final DishesMapper dishesMapper;

  @Override
  @Transactional
  public void create(DishDto dishDto) {
    Dish dish = dishesRepository.save(dishesMapper.toEntity(dishDto));
    log.info("\033[1;94mDish created: \033[0;97m{}\033[0m", dish);
    dishDto.getIngredients()
        .forEach(ingredientDto -> {
          Ingredient ingredient = ingredientMapper.toEntity(ingredientDto);
          ingredient.setDish(dish);
          ingredientRepository.save(ingredient);
        });
    log.info("\033[1;92mIngredients added to dish\033[0m");
  }

  @Override
  public DishDto read(UUID dishesId) {
    log.info("\033[1;94mFind dish by id: \033[0;97m{}\033[0m", dishesId);
    Dish dish = dishesRepository.findById(dishesId)
        .orElseThrow(() -> new BaseException(this.getClass().getSimpleName() + NOT_FOUND));
    log.info("\033[1;92mSet ingredients to dish\033[0m");
    dish.setIngredients(ingredientRepository.findAllByDish(dish));
    return dishesMapper.toDto(dish);
  }

  public DishDto readByName(String name) {
    log.info("\033[1;94mReading dish by name: \033[0;97m{}\033[0m", name);
    return dishesMapper.toDto(dishesRepository
        .findByName(name)
        .orElseThrow(() -> new BaseException(this.getClass().getSimpleName() + NOT_FOUND)));
  }

  @Override
  public List<DishDto> readAll() {
    log.info("\033[1;94mReading all dishes\033[0m");
    return dishesRepository.findAll().stream()
        .map(dishesMapper::toDto)
        .toList();
  }

  public List<DishDto> readAllByCategory(Category category) {
    log.info("\033[1;94mReading all dishes by category: \033[0;97m{}\033[0m", category);
    return dishesRepository.findAllByCategory(category).stream()
        .map(dishesMapper::toDto)
        .toList();
  }

  @Override
  public void update(DishDto dishDto) {
    log.info("\033[1;94mUpdate dish: \033[0;97m{}\033[0m", dishDto.getName());
    dishesRepository.save(dishesMapper.toEntity(dishDto));
  }

  @Override
  public void delete(UUID dishesId) {
    log.info("\033[1;94mDelete dish: \033[0;97m{}\033[0m", dishesId);
    dishesRepository
        .findById(dishesId)
        .ifPresent(dishesRepository::delete);
  }

  public Dish getDish() {
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
