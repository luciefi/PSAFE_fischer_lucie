package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.model.Person;

import java.util.List;

public interface IPersonService {
    List<Person> getPersons();

    Person saveNewPerson(Person newPerson);

    Person findByName(String wholeName);

    void deletePerson(String wholeName);
}
