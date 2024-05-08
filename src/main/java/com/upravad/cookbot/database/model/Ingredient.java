package com.upravad.cookbot.database.model;

import static jakarta.persistence.CascadeType.PERSIST;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "ingredients")
@NoArgsConstructor
@AllArgsConstructor
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

  @Override
  public String toString() {
    return "Ingredient{" +
           "id=" + id +
           ", name=" + name +
           ", weight=" + weight +
           ", cost=" + cost +
           '}';
  }
}