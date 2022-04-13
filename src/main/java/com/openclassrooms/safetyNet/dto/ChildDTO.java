package com.openclassrooms.safetyNet.dto;

import com.openclassrooms.safetyNet.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChildDTO {
    private String firstName;
    private String lastName;
    private int age;
    List<Person> householdMembers;
}
