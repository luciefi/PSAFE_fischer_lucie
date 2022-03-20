package com.openclassrooms.safetyNet.model;

import lombok.Data;

@Data
public class LightweightPerson {


    public LightweightPerson(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    public String recuperateFormattedFullName() {
        return firstName + "_" + lastName;
    }
}
