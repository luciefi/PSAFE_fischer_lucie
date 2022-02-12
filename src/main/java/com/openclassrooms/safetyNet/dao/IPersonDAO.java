package com.openclassrooms.safetyNet.dao;

import com.openclassrooms.safetyNet.model.Person;

import java.util.List;

public interface IPersonDAO {
    List<Person> getPersons();

    Person saveNewPerson(Person newPerson);

    Person findByName(String fullName);

    void deletePerson(String fullName);
}
