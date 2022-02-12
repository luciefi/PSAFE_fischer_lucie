package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.model.Person;

import java.util.List;

public interface IPersonService {
    List<Person> getAll();

    Person save(Person newPerson);

    Person findByName(String fullName);

    void delete(String fullName);

    Person update(Person updatedPerson);
}
