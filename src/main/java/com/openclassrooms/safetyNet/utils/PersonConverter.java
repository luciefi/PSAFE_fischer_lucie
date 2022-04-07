package com.openclassrooms.safetyNet.utils;

import com.openclassrooms.safetyNet.exceptions.InvalidFormattedFullNameException;
import com.openclassrooms.safetyNet.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class PersonConverter {
    private PersonConverter(){}

    public static LightweightPerson convertToLightweight(Person person) {
        if(person == null){
            return null;
        }
        return new LightweightPerson(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone());
    }

    public static Child convertToChild(Person person, int age, List<Person> householdList) {
        if(person == null){
            return null;
        }
        for (Person p : householdList) {
            if (p == null) {
                return null;
            }
        }
        List<Person> filteredHousehold = householdList.stream().filter(p -> !(p.getFirstName().equals(person.getFirstName()) && person.getLastName().equals(p.getLastName()))).collect(Collectors.toList());
        return new Child(person.getFirstName(), person.getLastName(), age, filteredHousehold);
    }

    public static PersonWithMedicalRecord convertToPersonWithMedicalRecord(Person person, MedicalRecord medicalRecord) {
        if(person == null || medicalRecord == null){
            return null;
        }

        return new PersonWithMedicalRecord(person.getFirstName(),
                person.getLastName(),
                person.getPhone(),
                MedicalRecordUtils.getPersonAge(medicalRecord),
                medicalRecord.getMedications(),
                medicalRecord.getAllergies()
        );
    }

    public static String convertToFormattedFullName(Person person){
        if(person == null){
            return null;
        }
        return convertToFormattedFullName(person.getFirstName(), person.getLastName());
    }

    public static String convertToFormattedFullName(String firstName, String lastName){
        return firstName + "_" + lastName;
    }

    public static String[] convertToNameArray(String fullName) {
        String[] nameArray = fullName.split("_");
        if(nameArray.length != 2){
            throw new InvalidFormattedFullNameException();
        }
        return nameArray;
    }

    public static PersonInfo convertToPersonInfo(Person person, MedicalRecord medicalRecord) {
        if(person == null || medicalRecord == null){
            return null;
        }
        return new PersonInfo(person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                MedicalRecordUtils.getPersonAge(medicalRecord),
                person.getEmail(),
                medicalRecord.getMedications(),
                medicalRecord.getAllergies()
        );
    }
}