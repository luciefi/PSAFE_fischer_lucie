package com.openclassrooms.safetyNet.utils;

import com.openclassrooms.safetyNet.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class PersonConverter {

    private PersonConverter(){}

    public static LightweightPerson convertToLightweight(Person person) {
        return new LightweightPerson(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone());
    }

    public static Child convertToChild(Person person, int age, List<Person> householdList) {
        List<Person> filteredHousehold = householdList.stream().filter(p -> !(p.getFirstName().equals(person.getFirstName()) && person.getLastName().equals(p.getLastName()))).collect(Collectors.toList());
        return new Child(person.getFirstName(), person.getLastName(), age, filteredHousehold);
    }

    public static PersonWithMedicalRecord convertToPersonWithMedicalRecord(Person person, MedicalRecord medicalRecord) {
        return new PersonWithMedicalRecord(person.getFirstName(),
                person.getLastName(),
                person.getPhone(),
                MedicalRecordUtils.getPersonAge(medicalRecord),
                medicalRecord.getMedications(),
                medicalRecord.getAllergies()
        );
    }
}