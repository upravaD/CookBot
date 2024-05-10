package com.upravad.cookbot.service.interfaces;

/**
 * This class provides functionality for working with a {@code Postgres} database.
 *
 * @param <I> The type of the identifier
 * @param <D> The type of the DTO object
 */
public interface PostgresService<I, D> {

  void create(D dto);
  D read(I id);
  D readByName(String name);
  void update(D dto);
  void delete(I id);

}
