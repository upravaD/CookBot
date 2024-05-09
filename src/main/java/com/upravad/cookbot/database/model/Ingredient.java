package com.upravad.cookbot.database.model;

import static jakarta.persistence.CascadeType.PERSIST;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

/**
 * Object representation of the ingredient table in the database.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "dish")
@Table(name = "ingredients")
public class Ingredient {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "weight")
  private Integer weight;

  @Column(name = "cost")
  private Double cost;

  @ManyToOne(cascade = PERSIST)
  @JoinColumn(name = "dish_id", referencedColumnName = "id")
  private Dish dish;

}
