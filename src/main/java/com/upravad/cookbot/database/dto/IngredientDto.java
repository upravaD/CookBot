package com.upravad.cookbot.database.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.upravad.cookbot.database.model.Ingredient}
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto implements Serializable {

  private UUID id;
  private String name;
  private Integer weight;
  private Double cost;

  @Override
  public String toString() {
    return "\n" + name + ": " + weight + " гр.";
  }
}
