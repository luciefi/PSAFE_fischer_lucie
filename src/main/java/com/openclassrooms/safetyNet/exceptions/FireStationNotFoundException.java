package com.openclassrooms.safetyNet.exceptions;

public class FireStationNotFoundException extends NotFoundException {
    public FireStationNotFoundException() {
        super("Fire station could not be found.");
    }
}
