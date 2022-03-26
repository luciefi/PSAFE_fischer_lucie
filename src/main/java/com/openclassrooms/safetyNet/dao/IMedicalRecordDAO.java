package com.openclassrooms.safetyNet.dao;

import com.openclassrooms.safetyNet.model.MedicalRecord;

import java.util.List;

public interface IMedicalRecordDAO {
    List<MedicalRecord> findAll();

    MedicalRecord save(MedicalRecord newMedicalRecord);

    MedicalRecord findById(String[] nameArray);

    void deleteById(String[] nameArray);

    MedicalRecord findByIdOrThrow(String[] strings);
}