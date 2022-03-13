package com.openclassrooms.safetyNet.exceptions;

public class MedicalRecordAlreadyExistsException extends AlreadyExistsException {
    public MedicalRecordAlreadyExistsException() {
        super("Medical record already exists for this name.");
    }
}
