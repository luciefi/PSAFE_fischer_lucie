package com.openclassrooms.safetyNet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PersonAlreadyExistsException extends AlreadyExistsException {
    public PersonAlreadyExistsException() {
        super("Person already exists.");
    }
}
