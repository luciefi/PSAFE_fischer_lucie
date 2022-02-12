package com.openclassrooms.safetyNet.controller;

import com.openclassrooms.safetyNet.model.Person;
import com.openclassrooms.safetyNet.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private IPersonService personService;

    @GetMapping("/persons")
    public ResponseEntity<?> getPersons() {
        List<Person> persons = personService.getAll();
        if (persons.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @PostMapping("/persons")
    public ResponseEntity<?> addPerson(@Valid @RequestBody Person newPerson) {
        personService.save(newPerson);
        //TODO retourner la vrai URI
        return ResponseEntity.created(URI.create("persons")).body(newPerson);
    }

    @PutMapping("/persons")
    public ResponseEntity<?> updatePerson(@Valid @RequestBody Person person) {
        Person updatedPerson = personService.update(person);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/persons/{fullName}")
    public ResponseEntity<?> deletePerson(@PathVariable("fullName") final String fullName) {
            personService.delete(fullName);
            return ResponseEntity.noContent().build();
    }

}
