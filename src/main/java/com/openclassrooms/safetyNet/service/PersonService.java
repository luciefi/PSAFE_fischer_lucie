package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.dao.IPersonDAO;
import com.openclassrooms.safetyNet.exceptions.PersonAlreadyExistsException;
import com.openclassrooms.safetyNet.exceptions.PersonNotFoundException;
import com.openclassrooms.safetyNet.model.Person;
import com.openclassrooms.safetyNet.utils.PersonConverter;
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
        logger.info("Adding person with name {}",
                PersonConverter.convertToFormattedFullName(newPerson));
        Person currentPerson = findByName(PersonConverter.convertToFormattedFullName(newPerson));
        if (currentPerson != null) {
            logger.error("Unable to add. A person with name {} already exist", PersonConverter.convertToFormattedFullName(newPerson));
            throw new PersonAlreadyExistsException();
        }
        return personDAO.save(newPerson);
    }

    @Override
    public Person findByName(String fullName) {
        logger.debug("Looking for person with name {}", fullName);
        String[] nameArray = PersonConverter.convertToNameArray(fullName);
        return personDAO.findByFirstAndLastNames(nameArray[0], nameArray[1]);
    }

    @Override
    public Person update(Person updatedPerson) {
        logger.info("Updating person with name {}",
                PersonConverter.convertToFormattedFullName(updatedPerson));
        Person currentPerson = findByName(PersonConverter.convertToFormattedFullName(updatedPerson));

        if (currentPerson == null) {
            logger.error("Unable to update. Person with name {} not found.", PersonConverter.convertToFormattedFullName(updatedPerson));
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
        String[] nameArray = PersonConverter.convertToNameArray(fullName);
        personDAO.deleteByFirstAndLastNames(nameArray[0], nameArray[1]);
    }
}
