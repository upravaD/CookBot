package com.upravad.cookbot.integration.database;

import static com.upravad.cookbot.database.enums.Category.BREAKFAST;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

import com.upravad.cookbot.config.ContainerConfiguration;
import com.upravad.cookbot.database.model.Dish;
import com.upravad.cookbot.database.model.Ingredient;
import com.upravad.cookbot.database.repository.DishesRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = {ContainerConfiguration.class})
class DishesRepoTest {

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
    Dish expected = getDish();

    log.info(expected.toString());

    assertThat(dish.getName()).isEqualTo(expected.getName());
    assertThat(dish.getCategory()).isEqualTo(expected.getCategory());
    assertThat(dish.getIngredients()).isNotNull();
    assertThat(dish.getRecipe()).isEqualTo(expected.getRecipe());
  }

  @Test
  @DisplayName("Find dish by name test")
  void readByNameTest() {
    Optional<Dish> optional = dishesRepository.findByName(dish.getName());
    assertTrue(optional.isPresent());
    Dish expected = optional.get();

    log.info(expected.toString());

    assertThat(dish.getName()).isEqualTo(expected.getName());
    assertThat(dish.getCategory()).isEqualTo(expected.getCategory());
    assertThat(dish.getIngredients()).isNotNull();
    assertThat(dish.getRecipe()).isEqualTo(expected.getRecipe());
  }

  @Test
  @DisplayName("Update dish test")
  void updateTest() {
    dish.setName("TEST");
    dishesRepository.save(dish);

    Optional<Dish> optional1 = dishesRepository.findById(dish.getId());
    assertTrue(optional1.isPresent());
    Dish expected = optional1.get();

    log.info(expected.toString());

    assertThat(dish.getName()).isEqualTo(expected.getName());
    assertThat(dish.getCategory()).isEqualTo(expected.getCategory());
    assertThat(dish.getIngredients()).isNotNull();
    assertThat(dish.getRecipe()).isEqualTo(expected.getRecipe());
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
