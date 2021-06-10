package com.example.fetchrewards.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"id"})
public class Payer {

  private @Id
  @GeneratedValue
  Long id;
  private String payer;
  private int points;

  public Payer(String payer, int points) {
    this.payer = payer;
    this.points = points;
  }
}
