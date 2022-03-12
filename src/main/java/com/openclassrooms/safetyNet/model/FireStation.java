package com.openclassrooms.safetyNet.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class FireStation {

    @NotBlank(message = "Address cannot be empty.")
    private String address;

    @Positive(message = "Station must be a positive number.")
    private int station;
}