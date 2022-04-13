package com.openclassrooms.safetyNet.dao;

import com.openclassrooms.safetyNet.model.Person;

import java.util.List;

public interface IPersonDAO {
    List<Person> findAll();

    Person save(Person newPerson);

    Person findByFirstAndLastNames(String firstName, String lastName);

    void deleteByFirstAndLastNames(String firstName, String lastName);

    List<Person> findByAddresses(List<String> addresses);

    List<Person> findByAddress(String address);

    List<Person> findByCity(String city);
}
