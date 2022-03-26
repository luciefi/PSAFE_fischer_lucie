package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.dao.IMedicalRecordDAO;
import com.openclassrooms.safetyNet.exceptions.InvalidFormattedFullNameException;
import com.openclassrooms.safetyNet.exceptions.MedicalRecordAlreadyExistsException;
import com.openclassrooms.safetyNet.exceptions.MedicalRecordNotFoundException;
import com.openclassrooms.safetyNet.model.MedicalRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService implements IMedicalRecordService {

    private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);

    private final IMedicalRecordDAO medicalRecordDAO;

    @Autowired
    public MedicalRecordService(IMedicalRecordDAO medicalRecordDAO) {
        this.medicalRecordDAO = medicalRecordDAO;
    }

    @Override
    public List<MedicalRecord> getAll() {
        logger.info("Fetching every medical record in the list");
        return medicalRecordDAO.findAll();
    }

    @Override
    public MedicalRecord save(MedicalRecord newMedicalRecord) {
        logger.info("Adding medical record with name {}", newMedicalRecord.recuperateFormattedFullName());
        MedicalRecord currentMedicalRecord = findByName(newMedicalRecord.recuperateFormattedFullName());
        if (currentMedicalRecord != null) {
            logger.error("Unable to add. A medical record with name {} already exist", newMedicalRecord.recuperateFormattedFullName());
            throw new MedicalRecordAlreadyExistsException();
        }
        return medicalRecordDAO.save(newMedicalRecord);
    }

    @Override
    public MedicalRecord findByName(String fullName) {
        String[] nameArray = fullName.split("_");
        if (nameArray.length != 2) {
            throw new InvalidFormattedFullNameException();
        }
        return medicalRecordDAO.findById(nameArray);
    }

    @Override
    public MedicalRecord update(MedicalRecord updatedMedicalRecord) {
        logger.info("Updating medical record with name {}", updatedMedicalRecord.recuperateFormattedFullName());
        MedicalRecord currentMedicalRecord = findByName(updatedMedicalRecord.recuperateFormattedFullName());

        if (currentMedicalRecord == null) {
            logger.error("Unable to update. Medical record with name {} not found.", updatedMedicalRecord.recuperateFormattedFullName());
            throw new MedicalRecordNotFoundException();
        }

        currentMedicalRecord.setMedications(updatedMedicalRecord.getMedications());
        currentMedicalRecord.setAllergies(updatedMedicalRecord.getAllergies());
        currentMedicalRecord.setBirthdate(updatedMedicalRecord.getBirthdate());
        return currentMedicalRecord;
    }

    @Override
    public void delete(String fullName) {
        logger.info("Deleting medical record with name {}", fullName);
        MedicalRecord currentMedicalRecord = findByName(fullName);
        if (currentMedicalRecord == null) {
            logger.error("Unable to delete. Medical record with name {} not found.", fullName);
            throw new MedicalRecordNotFoundException();
        }
        medicalRecordDAO.deleteById(fullName.split("_"));
    }

}
