package com.openclassrooms.safetyNet.dao;

import com.openclassrooms.safetyNet.model.MedicalRecord;

import java.util.List;

public interface IMedicalRecordDAO {
    List<MedicalRecord> findAll();

    MedicalRecord save(MedicalRecord newMedicalRecord);

    MedicalRecord findByFirstAndLastNames(String firstName, String lastName);

    void deleteByFirstAndLastNames(String firstName, String lastName);

}