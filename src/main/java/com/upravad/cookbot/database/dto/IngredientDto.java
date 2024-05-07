package com.upravad.cookbot.database.dto;

import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;

/**
 * DTO for {@link com.upravad.cookbot.database.model.Ingredient}
 */
@Builder
public record IngredientDto(UUID id,
                            String name,
                            Integer weight,
                            Double cost) implements Serializable {

}