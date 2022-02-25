package com.openclassrooms.safetyNet.exceptions;

public class PersonNotFoundException extends NotFoundException {
    public PersonNotFoundException() {
        super("Person could not be found.");
    }
}
