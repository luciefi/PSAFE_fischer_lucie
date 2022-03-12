package com.openclassrooms.safetyNet.exceptions;

public class FireStationAlreadyExistsException extends AlreadyExistsException {
    public FireStationAlreadyExistsException() {
        super("Fire station already exists for this address.");
    }
}
