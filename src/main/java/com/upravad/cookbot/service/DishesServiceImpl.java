package com.upravad.cookbot.service;

import static com.upravad.cookbot.util.Color.GREEN_BRIGHT;
import static com.upravad.cookbot.util.Color.RESET;

import com.upravad.cookbot.database.dto.DishDto;
import com.upravad.cookbot.database.mapper.DishesMapper;
import com.upravad.cookbot.database.model.Dish;
import com.upravad.cookbot.database.repository.DishesRepository;
import com.upravad.cookbot.database.repository.IngredientRepository;
import com.upravad.cookbot.exception.BaseException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DishesServiceImpl implements DishesService {

  private final DishesRepository dishesRepository;
  private final IngredientRepository ingredientRepository;
  private final DishesMapper dishesMapper;

  private static final String BRACE = GREEN_BRIGHT + "{}" + RESET;

  @Override
  public void create(DishDto dishDto) {
    log.info("Creating dish {}", dishDto);
    dishesRepository.save(dishesMapper.toEntity(dishDto));
  }

  public DishDto readByName(String name) {
    log.info("Reading dish by name: " + BRACE, name);
    return dishesMapper.toDto(dishesRepository
        .findByName(name)
        .orElseThrow(() -> new BaseException("Dish not found")));
  }

  @Override
  public DishDto read(UUID dishesId) {
    log.info("Reading dish: " + BRACE, dishesId);
    return dishesMapper.toDto(dishesRepository
        .findById(dishesId)
        .orElseThrow(() -> new BaseException("Dish not found")));
  }

  @Override
  public void update(DishDto dishDto) {
    log.info("Update dish: " + BRACE, dishDto.name());
    dishesRepository.save(dishesMapper.toEntity(dishDto));
  }

  @Override
  public void delete(UUID dishesId) {
    log.info("Delete dish: " + BRACE, dishesId);
    dishesRepository
        .findById(dishesId)
        .ifPresent(dishesRepository::delete);
  }
}
