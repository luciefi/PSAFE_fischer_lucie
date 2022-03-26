package com.openclassrooms.safetyNet.utils;

import com.openclassrooms.safetyNet.model.Child;
import com.openclassrooms.safetyNet.model.LightweightPerson;
import com.openclassrooms.safetyNet.model.Person;

import java.util.List;
import java.util.stream.Collectors;

public class PersonConverter {

    public static LightweightPerson convertToLightweight(Person person) {
        return new LightweightPerson(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone());
    }

    public static Child convertToChild(Person person, int age, List<Person> householdList) {
        List<Person> filteredHousehold = householdList.stream().filter(p -> !(p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName()))).collect(Collectors.toList());
        return new Child(person.getFirstName(), person.getLastName(), age, filteredHousehold);
    }
}