package com.openclassrooms.safetyNet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStation {

    @NotBlank(message = "Address cannot be empty.")
    private String address;

    @Positive(message = "Station must be a positive number.")
    private int station;
}