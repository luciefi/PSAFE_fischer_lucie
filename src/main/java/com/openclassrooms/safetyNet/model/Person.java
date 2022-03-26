package com.openclassrooms.safetyNet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    public Person(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

    @NotBlank(message = "First name cannot be empty.")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty.")
    private String lastName;

    @NotBlank(message = "Address cannot be empty.")
    private String address;

    @NotBlank(message = "City cannot be empty.")
    private String city;

    @Positive(message = "Zip must be a positive number.")
    private int zip;

    @NotBlank(message = "Phone cannot be empty.")
    private String phone;

    @NotBlank(message = "Email cannot be empty.")
    private String email;

    public String recuperateFormattedFullName(){
        return firstName + "_" + lastName;
    }
}
