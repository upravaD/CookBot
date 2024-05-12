package com.upravad.cookbot.database.repository;

import com.upravad.cookbot.database.enums.Category;
import com.upravad.cookbot.database.model.Dish;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishesRepository extends JpaRepository<Dish, UUID> {

  Optional<Dish> findByName(String name);

  List<Dish> findAllByCategory(Category category);
}