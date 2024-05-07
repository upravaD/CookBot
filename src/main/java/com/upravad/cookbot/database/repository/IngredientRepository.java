package com.upravad.cookbot.database.repository;

import com.upravad.cookbot.database.model.Ingredient;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {

}