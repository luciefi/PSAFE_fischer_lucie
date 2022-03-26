package com.openclassrooms.safetyNet.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Child {
    private String firstName;
    private String lastName;
    private int age;
    List<Person> householdMembers;
}
