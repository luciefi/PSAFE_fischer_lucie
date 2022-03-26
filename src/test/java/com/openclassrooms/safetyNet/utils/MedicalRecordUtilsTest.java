package com.openclassrooms.safetyNet.utils;

import com.openclassrooms.safetyNet.model.MedicalRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicalRecordUtilsTest {

    @Test
    public void isAChildTest() {
        MedicalRecord medicalRecord = new MedicalRecord();
        Date childBirthDate = new Date();
        medicalRecord.setBirthdate(childBirthDate);
        Assertions.assertTrue(MedicalRecordUtils.isAChild(medicalRecord));
    }

    @Test
    public void isNotAChildTest() throws ParseException {
        MedicalRecord medicalRecord = new MedicalRecord();
        String date_string = "01/01/1989";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date adultBirthDate = formatter.parse(date_string);
        medicalRecord.setBirthdate(adultBirthDate);
        Assertions.assertFalse(MedicalRecordUtils.isAChild(medicalRecord));
    }
}
