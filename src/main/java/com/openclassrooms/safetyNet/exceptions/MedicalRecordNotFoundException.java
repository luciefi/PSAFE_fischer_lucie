package com.openclassrooms.safetyNet.exceptions;

public class MedicalRecordNotFoundException extends NotFoundException {
    public MedicalRecordNotFoundException() {
        super("Medical record could not be found.");
    }
}
