package com.openclassrooms.safetyNet.exceptions;

public class InvalidException extends IllegalArgumentException{
    public InvalidException(String s) {
        super(s);
    }
}
