package com.openclassrooms.safetyNet.dao;

import com.openclassrooms.safetyNet.DataSource;
import com.openclassrooms.safetyNet.exceptions.MedicalRecordNotFoundException;
import com.openclassrooms.safetyNet.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MedicalRecordDAO implements IMedicalRecordDAO {

    private final DataSource dataSource;

    @Autowired
    public MedicalRecordDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<MedicalRecord> findAll() {
        return dataSource.getMedicalrecords();
    }

    @Override
    public MedicalRecord save(MedicalRecord newMedicalRecord) {
        List<MedicalRecord> medicalRecordList = dataSource.getMedicalrecords();
        medicalRecordList.add(newMedicalRecord);
        return newMedicalRecord;
    }

    @Override
    public MedicalRecord findById(String firstName, String lastName) {
        List<MedicalRecord> medicalRecordList = dataSource.getMedicalrecords();
        List<MedicalRecord> foundMedicalRecordList = medicalRecordList.stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))
                .collect(Collectors.toList());
        if (foundMedicalRecordList.size() > 0) {
            return foundMedicalRecordList.get(0);
        }
        return null;
    }

    @Override
    public void deleteById(String firstName, String lastName) {
        dataSource.getMedicalrecords().remove(findById(firstName, lastName));
    }

    //TODO déplacer dans service
    @Override
    public MedicalRecord findByIdOrThrow(String firstName, String lastName) {
        MedicalRecord medicalRecord = findById(firstName, lastName);
        if (medicalRecord == null) {
            throw new MedicalRecordNotFoundException();
        }
        return medicalRecord;
    }
}