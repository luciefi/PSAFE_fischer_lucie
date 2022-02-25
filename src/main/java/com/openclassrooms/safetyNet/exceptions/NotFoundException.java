package com.openclassrooms.safetyNet.exceptions;

public class NotFoundException extends IllegalArgumentException {
    public NotFoundException(String s) {
        super(s);
    }
}
