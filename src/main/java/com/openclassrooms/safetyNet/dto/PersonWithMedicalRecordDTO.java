package com.openclassrooms.safetyNet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PersonWithMedicalRecordDTO {

    private String firstName;

    private String lastName;

    private String phone;

    private int age;

    private List<String> medications;

    private List<String> allergies;
}
