package com.upravad.cookbot.database.model;

import static jakarta.persistence.EnumType.STRING;

import com.upravad.cookbot.database.enums.Category;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import java.util.UUID;
import java.util.Set;

/**
 * Object representation of the dishes table in the database.
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dishes")
public class Dish {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Enumerated(STRING)
  @Column(name = "category", nullable = false)
  private Category category;

  @OneToMany(mappedBy = "dish", fetch = FetchType.EAGER)
  private Set<Ingredient> ingredients;

  @Column(name = "recipe", nullable = false)
  private String recipe;

  @Column(name = "image_url", nullable = false)
  private String imageUrl;

  @Column(name = "price", nullable = false)
  private Double price;

}
