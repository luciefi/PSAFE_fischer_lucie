package com.openclassrooms.safetyNet.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddressWithPersonListingDTO {
    private String address;
    List<PersonWithMedicalRecordDTO> personListing;
}