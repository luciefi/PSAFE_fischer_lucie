package com.openclassrooms.safetyNet.dto;

import lombok.Data;

@Data
public class LightweightPersonDTO {

    public LightweightPersonDTO(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

    private String firstName;

    private String lastName;

    private String address;

    private String phone;
}
