package com.upravad.cookbot.database.repository;

import com.upravad.cookbot.database.model.Dish;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DishesRepository extends JpaRepository<Dish, UUID> {

  Optional<Dish> findByName(String name);

}