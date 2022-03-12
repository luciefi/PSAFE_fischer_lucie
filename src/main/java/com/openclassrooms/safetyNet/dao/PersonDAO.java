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
    public Person findById(String[] nameArray) {
        List<Person> personList = dataSource.getPersons();
        List<Person> foundPersonList = personList.stream()
                .filter(person -> person.getFirstName().equals(nameArray[0]) && person.getLastName().equals(nameArray[1]))
                .collect(Collectors.toList());
        if (foundPersonList.size() > 0) {
            return foundPersonList.get(0);
        }
        return null;
    }

    @Override
    public void deleteById(String[] nameArray) {
        dataSource.getPersons().remove(findById(nameArray));
    }
}