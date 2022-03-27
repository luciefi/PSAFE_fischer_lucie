package com.openclassrooms.safetyNet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {

    private String firstName;
    private String lastName;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date birthdate;
    private List<String> medications;
    private List<String> allergies;

    public String recuperateFormattedFullName() {
        return firstName + "_" + lastName;
    }
}
