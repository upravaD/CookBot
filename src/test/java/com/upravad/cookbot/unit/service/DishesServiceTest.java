package com.upravad.cookbot.unit.service;

import static com.upravad.cookbot.database.enums.Category.BREAKFAST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.upravad.cookbot.config.ContainerConfiguration;
import com.upravad.cookbot.database.dto.IngredientDto;
import com.upravad.cookbot.database.model.Dish;
import com.upravad.cookbot.database.model.Ingredient;
import com.upravad.cookbot.database.repository.DishesRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ContainerConfiguration.class})
class DishesServiceTest {

  @Autowired
  DishesRepository dishesRepository;
  private Dish dish;

  @BeforeEach
  void setup() {
    dish = dishesRepository.save(getDish());
  }
  @AfterEach
  void delete() {
    dishesRepository.deleteAll();
  }

  @Test
  @DisplayName("Create and save dish test")
  void createTest() {
    Dish dto = getDish();

    assertEquals(dish.getName(), dto.getName());
    assertEquals(dish.getCategory(), dto.getCategory());
    assertNotNull(dish.getIngredients());
    assertEquals(dish.getRecipe(), dto.getRecipe());
  }

  @Test
  @DisplayName("Find dish by name test")
  void readByNameTest() {
    Optional<Dish> optional = dishesRepository.findByName(dish.getName());
    assertTrue(optional.isPresent());
    Dish expected = optional.get();

    assertEquals(dish.getName(), expected.getName());
    assertEquals(dish.getCategory(), expected.getCategory());
    assertNotNull(dish.getIngredients());
    assertEquals(dish.getRecipe(), expected.getRecipe());
  }

  @Test
  @DisplayName("Find dish by name test")
  void updateTest() {
    Optional<Dish> optional = dishesRepository.findByName(dish.getName());
    assertTrue(optional.isPresent());

    Dish updated = optional.get();
    updated.setName("TEST");
    dishesRepository.save(updated);

    Optional<Dish> optional1 = dishesRepository.findById(dish.getId());
    assertTrue(optional1.isPresent());
    Dish expected = optional1.get();

    assertEquals(updated.getName(), expected.getName());
    assertEquals(updated.getCategory(), expected.getCategory());
    assertNotNull(updated.getIngredients());
    assertEquals(updated.getRecipe(), expected.getRecipe());
  }

  private Dish getDish() {
    return Dish.builder()
        .name("Овсянка с яблоками")
        .category(BREAKFAST)
        .ingredients(getIngredients())
        .recipe("TEXT")
        .imageUrl("https://wellnessplay.com.br/wp-content/uploads/2019/09/Depositphotos_38611825_xl-2015.jpg")
        .price(getPrice(getIngredients()))
        .build();
  }

  private List<Ingredient> getIngredients() {
    return List.of(
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

  private Double getPrice(List<Ingredient> ingredients) {
    return ingredients.stream()
        .mapToDouble(Ingredient::getCost)
        .sum();
  }
}
