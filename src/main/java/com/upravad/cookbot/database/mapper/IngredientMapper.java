package com.upravad.cookbot.database.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import com.upravad.cookbot.database.dto.IngredientDto;
import com.upravad.cookbot.database.model.Ingredient;
import org.mapstruct.Mapper;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = SPRING)
public interface IngredientMapper {

  IngredientDto toDto(Ingredient ingredient);
  Ingredient toEntity(IngredientDto ingredientDto);

}
