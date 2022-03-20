package com.openclassrooms.safetyNet.utils;

import com.openclassrooms.safetyNet.model.LightweightPerson;
import com.openclassrooms.safetyNet.model.Person;

public class PersonConverter {

    public static Person convertToPerson(LightweightPerson lwPerson) {
        return new Person(lwPerson.getFirstName(), lwPerson.getLastName(), lwPerson.getAddress(), lwPerson.getPhone());
    }

    public static LightweightPerson convertToLightweight(Person person) {
        return new LightweightPerson(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone());
    }
}