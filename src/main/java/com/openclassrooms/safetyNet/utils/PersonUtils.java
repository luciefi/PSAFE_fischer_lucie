package com.openclassrooms.safetyNet.utils;

import com.openclassrooms.safetyNet.model.MedicalRecord;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PersonUtils {

    public static int getPersonAge(String firstName, String lastName, List<MedicalRecord> medicalRecords) {
        Optional<Date> birthDate = medicalRecords
                .stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))
                .map(medicalRecord -> medicalRecord.getBirthdate())
                .findFirst();

        LocalDate birthLocalDate = birthDate.get().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        Period period = Period.between(birthLocalDate, LocalDate.now());
        return period.getYears();
    }

    public static boolean isAChild(String firstName, String lastName, List<MedicalRecord> medicalRecords) {
        if (getPersonAge(firstName, lastName, medicalRecords) < 18) {
            return true;
        }
        return false;
    }
}
