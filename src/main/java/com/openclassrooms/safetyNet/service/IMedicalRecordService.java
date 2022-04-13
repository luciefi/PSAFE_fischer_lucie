package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.model.MedicalRecord;

import java.util.List;

public interface IMedicalRecordService {
    List<MedicalRecord> getAll();

    MedicalRecord save(MedicalRecord newMedicalRecord);

    MedicalRecord findByName(String fullName);

    void delete(String fullName);

    MedicalRecord update(MedicalRecord updatedPerson);

    MedicalRecord findByFirstAndLastNamesOrThrow(String firstName, String lastName);
}
