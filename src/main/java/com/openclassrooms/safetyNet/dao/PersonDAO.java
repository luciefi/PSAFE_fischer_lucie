package com.openclassrooms.safetyNet.dao;

import com.openclassrooms.safetyNet.DataSource;
import com.openclassrooms.safetyNet.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class PersonDAO implements IPersonDAO {

    @Autowired
    DataSource dataSource;

    @Override
    public List<Person> getPersons(){
        return dataSource.getPersons();
    }

    @Override
    public Person saveNewPerson(Person newPerson) {
        List<Person> personList = dataSource.getPersons();
        personList.add(newPerson);
        return personList.get(personList.size() - 1);
    }

    @Override
    public Person findByName(String wholeName) {
        String[] nameArray = wholeName.split("_");
        List<Person> personList = dataSource.getPersons();
        List<Person> foundPersonList = personList.stream().filter(person -> Objects.equals(person.getFirstName(), nameArray[0])).filter(person -> Objects.equals(person.getLastName(), nameArray[1])).collect(Collectors.toList());
        if(foundPersonList.size() > 0){
            return foundPersonList.get(0);
        }
        return null;
    }

    @Override
    public void deletePerson(String wholeName) {
        dataSource.getPersons().remove(findByName(wholeName));
    }

}
