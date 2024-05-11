package com.upravad.cookbot.service.interfaces;

import java.util.List;

/**
 * This class provides functionality for working with a {@code Postgres} database.
 *
 * @param <I> The type of the identifier
 * @param <D> The type of the DTO object
 */
public interface PostgresService<I, D> {

  void create(D dto);
  D read(I id);
  List<D> readAll();
  void update(D dto);
  void delete(I id);

}
