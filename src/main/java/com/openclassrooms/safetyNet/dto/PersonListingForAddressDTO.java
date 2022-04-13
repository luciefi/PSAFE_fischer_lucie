package com.openclassrooms.safetyNet.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PersonListingForAddressDTO {
    int fireStation;
    List<PersonWithMedicalRecordDTO> personsListForAddress = new ArrayList<>();
}
