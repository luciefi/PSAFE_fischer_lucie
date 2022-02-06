package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.dao.IPersonDAO;
import com.openclassrooms.safetyNet.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements IPersonService{

    @Autowired
    private IPersonDAO personDAO;

    @Override
    public List<Person> getPersons() {
        return personDAO.getPersons();
    }

    public Person saveNewPerson(Person newPerson) {
        return personDAO.saveNewPerson(newPerson);
    }

    @Override
    public Person findByName(String wholeName) {
        return personDAO.findByName(wholeName);
    }

    @Override
    public void deletePerson(String wholeName) {
        personDAO.deletePerson(wholeName);
    }


}
