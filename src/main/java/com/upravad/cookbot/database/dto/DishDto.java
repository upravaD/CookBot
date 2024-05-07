package com.upravad.cookbot.database.dto;

import com.upravad.cookbot.database.enums.Category;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

/**
 * DTO for {@link com.upravad.cookbot.database.model.Dish}
 */
@Builder
public record DishDto(UUID id,
                      String name,
                      Category category,
                      List<IngredientDto> ingredients,
                      String recipe,
                      String imageUrl,
                      Double price) implements Serializable {

}