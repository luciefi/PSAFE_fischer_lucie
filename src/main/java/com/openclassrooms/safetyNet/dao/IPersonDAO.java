package com.openclassrooms.safetyNet.dao;

import com.openclassrooms.safetyNet.model.Person;

import java.util.List;

public interface IPersonDAO {
    List<Person> findAll();

    Person save(Person newPerson);

    Person findById(String[] nameArray);

    void deleteById(String[] nameArray);
}
