package com.openclassrooms.safetyNet.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class AddressWithPersonList {
    private String address;
    List<PersonWithMedicalRecord> addressWithPersonLists;
}
