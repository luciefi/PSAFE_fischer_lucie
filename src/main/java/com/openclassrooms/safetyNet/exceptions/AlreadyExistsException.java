package com.openclassrooms.safetyNet.exceptions;

public class AlreadyExistsException extends IllegalArgumentException {
    public AlreadyExistsException(String s) {
        super(s);
    }
}
