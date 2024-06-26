package com.upravad.cookbot.database.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

import com.upravad.cookbot.database.dto.DishDto;
import com.upravad.cookbot.database.model.Dish;
import org.mapstruct.Mapper;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = SPRING)
public interface DishesMapper {

  DishDto toDto(Dish dish);
  Dish toEntity(DishDto dishDto);

}
