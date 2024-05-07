package com.upravad.cookbot.database.mapper;

import com.upravad.cookbot.database.dto.DishDto;
import com.upravad.cookbot.database.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DishesMapper {

  DishDto toDto(Dish dish);
  Dish toEntity(DishDto dishDto);

}
