package com.upravad.cookbot.service;

import com.upravad.cookbot.database.dto.DishDto;
import java.util.UUID;

public interface DishesService {
  void create(DishDto dishDto);
  DishDto read(UUID dishesId);
  DishDto readByName(String name);
  void update(DishDto dishDto);
  void delete(UUID dishesId);
}
