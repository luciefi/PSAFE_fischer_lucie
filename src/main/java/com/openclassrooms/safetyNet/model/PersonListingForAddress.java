package com.openclassrooms.safetyNet.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PersonListingForAddress {
    int fireStation;
    List<PersonWithMedicalRecord> personsListForAddress = new ArrayList<>();
}
