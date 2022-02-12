package com.openclassrooms.safetyNet.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Person {

    @NotBlank(message = "First name cannot be empty.")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty.")
    private String lastName;

    @NotBlank(message = "Address cannot be empty.")
    private String address;

    @NotBlank(message = "City cannot be empty.")
    private String city;

    @NotNull(message = "Zip cannot be null.")
    private int zip;

    @NotBlank(message = "Phone cannot be empty.")
    private String phone;

    @NotBlank(message = "Email cannot be empty.")
    private String email;

    public String recuperateFormattedFullName(){
        return firstName + "_" + lastName;
    }
}
