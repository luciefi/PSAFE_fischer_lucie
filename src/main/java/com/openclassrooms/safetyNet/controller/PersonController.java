package com.openclassrooms.safetyNet.controller;

import com.openclassrooms.safetyNet.model.Person;
import com.openclassrooms.safetyNet.service.IPersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    @Autowired
    private IPersonService personService;

    @GetMapping("/persons")
    public List<Person> getPersons() {
        return personService.getPersons();
    }

    @PostMapping("/persons")
    public Person addPerson(@RequestBody Person newPerson) {
        logger.info("Creating person with name {} {}", newPerson.getFirstName(), newPerson.getLastName());

        return personService.saveNewPerson(newPerson);
    }

    @PutMapping("/persons/{wholeName}")
    public Person updatePerson(@PathVariable("wholeName") final String wholeName, @RequestBody Person person) {
        logger.info("Updating person with name {}", wholeName);
        Person currentPerson = personService.findByName(wholeName);

        if (currentPerson == null) {
            logger.error("Unable to update. Person with name {} not found.", wholeName);
            return null;
        }

        currentPerson.setAddress(person.getAddress());
        currentPerson.setCity(person.getCity());
        currentPerson.setZip(person.getZip());
        currentPerson.setPhone(person.getPhone());
        currentPerson.setEmail(person.getEmail());

        return personService.findByName(wholeName);
    }

    @DeleteMapping("/persons/{wholeName}")
    public void deletePerson(@PathVariable("wholeName") final String wholeName) {
        logger.info("Deleting person with name {}", wholeName);
        personService.deletePerson(wholeName);
    }
}
