package com.openclassrooms.safetyNet.exceptions;

public class InvalidFormattedFullNameException extends InvalidException {
    public InvalidFormattedFullNameException() {
        super("Formatted name is not valid.");
    }
}
