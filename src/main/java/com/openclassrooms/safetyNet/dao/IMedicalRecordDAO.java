package com.openclassrooms.safetyNet.dao;

import com.openclassrooms.safetyNet.model.MedicalRecord;

import java.util.List;

public interface IMedicalRecordDAO {
    List<MedicalRecord> findAll();

    MedicalRecord save(MedicalRecord newMedicalRecord);

    MedicalRecord findById(String firstName, String lastName);

    void deleteById(String firstName, String lastName);

    MedicalRecord findByIdOrThrow(String firstName, String lastName);
}