package com.brianeno.swagger3.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id = 0;

    @NotBlank
    @Size(min = 0, max = 20)
    private String vin;

    @NotBlank
    @Size(min = 0, max = 8)
    private Integer wattage;

    @NotBlank
    private boolean completed;

    public ChargeSession(String vin, Integer wattage, boolean completed) {
        this.vin = vin;
        this.wattage = wattage;
        this.completed = completed;
    }
}
