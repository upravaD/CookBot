package com.upravad.cookbot.database.dto;

import com.upravad.cookbot.database.enums.Category;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.io.Serializable;
import java.util.UUID;
import java.util.Set;

/**
 * DTO for {@link com.upravad.cookbot.database.model.Dish}
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishDto implements Serializable {
  private UUID id;
  private String name;
  private Category category;
  private Set<IngredientDto> ingredients;
  private String recipe;
  private String imageUrl;
  private Double price;

  @Override
  public String toString() {
    return name + ": { " + "id=" + id +
           ", \ncategory=" + category +
           ", \ningredients= " + ingredients +
           ", \nrecipe=" + recipe +
           ", \nimageUrl=" + imageUrl +
           ", \nprice=" + price +
           " }\n";
  }

}
