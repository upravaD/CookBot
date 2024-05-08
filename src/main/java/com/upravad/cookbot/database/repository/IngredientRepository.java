package com.upravad.cookbot.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.upravad.cookbot.database.model.Ingredient;
import com.upravad.cookbot.database.model.Dish;
import java.util.UUID;
import java.util.Set;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {

  Set<Ingredient> findAllByDish(Dish dish);

}