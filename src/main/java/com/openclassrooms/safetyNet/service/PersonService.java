package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.dao.IPersonDAO;
import com.openclassrooms.safetyNet.exceptions.InvalidFormattedFullNameException;
import com.openclassrooms.safetyNet.exceptions.PersonAlreadyExistsException;
import com.openclassrooms.safetyNet.exceptions.PersonNotFoundException;
import com.openclassrooms.safetyNet.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements IPersonService {

    private static final Logger logger = LogManager.getLogger(PersonService.class);

    private final IPersonDAO personDAO;

    @Autowired
    public PersonService(IPersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public List<Person> getAll() {
        logger.info("Fetching every person in the list");
        return personDAO.findAll();
    }

    @Override
    public Person save(Person newPerson) {
        logger.info("Adding person with name {}", newPerson.recuperateFormattedFullName());
        Person currentPerson = findByName(newPerson.recuperateFormattedFullName());
        if (currentPerson != null) {
            logger.error("Unable to add. A person with name {} already exist", newPerson.recuperateFormattedFullName());
            throw new PersonAlreadyExistsException();
        }
        return personDAO.save(newPerson);
    }

    @Override
    public Person findByName(String fullName) {
        String[] nameArray = fullName.split("_");
        if(nameArray.length != 2){
            throw new InvalidFormattedFullNameException();
        }
        return personDAO.findById(nameArray);
    }

    @Override
    public Person update(Person updatedPerson) {
        logger.info("Updating person with name {}", updatedPerson.recuperateFormattedFullName());
        Person currentPerson = findByName(updatedPerson.recuperateFormattedFullName());

        if (currentPerson == null) {
            logger.error("Unable to update. Person with name {} not found.", updatedPerson.recuperateFormattedFullName());
            throw new PersonNotFoundException();
        }

        currentPerson.setAddress(updatedPerson.getAddress());
        currentPerson.setCity(updatedPerson.getCity());
        currentPerson.setZip(updatedPerson.getZip());
        currentPerson.setPhone(updatedPerson.getPhone());
        currentPerson.setEmail(updatedPerson.getEmail());
        return currentPerson;
    }

    @Override
    public void delete(String fullName) {
        logger.info("Deleting person with name {}", fullName);
        Person currentPerson = findByName(fullName);
        if (currentPerson == null) {
            logger.error("Unable to delete. Person with name {} not found.", fullName);
            throw new PersonNotFoundException();
        }
        personDAO.deleteById(fullName.split("_"));
    }

}
