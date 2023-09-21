package com.brianeno.swagger3.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChargeSession {

  public ChargeSession(String vin, Integer wattage, boolean completed) {
    this.vin = vin;
    this.wattage = wattage;
    this.completed = completed;
  }

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private long id = 0;


  private String vin;

  private Integer wattage;

  private boolean completed;
}
