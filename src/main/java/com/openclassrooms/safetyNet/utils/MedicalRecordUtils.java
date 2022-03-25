package com.openclassrooms.safetyNet.utils;

import com.openclassrooms.safetyNet.model.MedicalRecord;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MedicalRecordUtils {

    public static int getPersonAge(MedicalRecord medicalRecord) {
        Date birthDate = medicalRecord.getBirthdate();

        LocalDate birthLocalDate = birthDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        Period period = Period.between(birthLocalDate, LocalDate.now());
        return period.getYears();
    }

    public static boolean isAChild(MedicalRecord medicalRecord) {
        return isAChild(getPersonAge(medicalRecord));
    }

    public static boolean isAChild(int age) {
        return age < 18;
    }
}
