package com.openclassrooms.safetyNet.dao;

import com.openclassrooms.safetyNet.DataSource;
import com.openclassrooms.safetyNet.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PersonDAO implements IPersonDAO {

    private final DataSource dataSource;

    @Autowired
    public PersonDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Person> findAll() {
        return dataSource.getPersons();
    }

    @Override
    public Person save(Person newPerson) {
        List<Person> personList = dataSource.getPersons();
        personList.add(newPerson);
        return newPerson;
    }

    @Override
    public Person findByFirstAndLastNames(String firstName, String lastName) {
        List<Person> personList = dataSource.getPersons();
        List<Person> foundPersonList =
                personList.stream().filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)).collect(Collectors.toList());
        if (foundPersonList.size() > 0) {
            return foundPersonList.get(0);
        }
        return null;
    }

    @Override
    public void deleteByFirstAndLastNames(String firstName, String lastName) {
        dataSource.getPersons().remove(findByFirstAndLastNames(firstName, lastName));
    }

    @Override
    public List<Person> findByAddresses(List<String> addresses) {
        return findAll().stream().filter(person -> addresses.contains(person.getAddress())).collect(Collectors.toList());
    }

    @Override
    public List<Person> findByAddress(String address) {
        return findAll().stream().filter(person -> address.equals(person.getAddress())).collect(Collectors.toList());
    }

    @Override
    public List<Person> findByCity(String city) {
        return findAll().stream().filter(person -> city.equals(person.getCity())).collect(Collectors.toList());
    }
}