package com.upravad.cookbot.database.model;

import static jakarta.persistence.EnumType.STRING;

import com.upravad.cookbot.database.enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "dishes")
@NoArgsConstructor
@AllArgsConstructor
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

  @Override
  public String toString() {
    return "Dish{" +
           "id=" + id +
           ", name=" + name +
           ", category=" + category +
           ", ingredients=" + ingredients +
           ", recipe=" + recipe +
           ", imageUrl=" + imageUrl +
           ", price=" + price +
           '}';
  }
}