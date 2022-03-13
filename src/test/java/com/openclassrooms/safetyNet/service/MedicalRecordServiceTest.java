package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.dao.IMedicalRecordDAO;
import com.openclassrooms.safetyNet.exceptions.InvalidFormattedFullNameException;
import com.openclassrooms.safetyNet.exceptions.MedicalRecordAlreadyExistsException;
import com.openclassrooms.safetyNet.exceptions.MedicalRecordNotFoundException;
import com.openclassrooms.safetyNet.model.MedicalRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    @Mock
    private IMedicalRecordDAO medicalRecordDAO;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @Test
    public void getAllMedicalRecordsTest() {
        when(medicalRecordDAO.findAll()).thenReturn(Collections.singletonList(new MedicalRecord()));
        assertEquals(1, medicalRecordService.getAll().size());
        verify(medicalRecordDAO, Mockito.times(1)).findAll();
    }

    @Test
    public void saveAlreadyExistingMedicalRecordTest() {
        when(medicalRecordDAO.findById(any(String[].class))).thenReturn(new MedicalRecord());
        assertThrows(MedicalRecordAlreadyExistsException.class, () -> medicalRecordService.save(new MedicalRecord()));
        verify(medicalRecordDAO, Mockito.times(0)).save(any(MedicalRecord.class));
        verify(medicalRecordDAO, Mockito.times(1)).findById(any(String[].class));
    }

    @Test
    public void saveNewMedicalRecordTest() {
        when(medicalRecordDAO.save(any(MedicalRecord.class))).thenReturn(new MedicalRecord());
        when(medicalRecordDAO.findById(any(String[].class))).thenReturn(null);
        medicalRecordService.save(new MedicalRecord());
        verify(medicalRecordDAO, Mockito.times(1)).findById(any(String[].class));
        verify(medicalRecordDAO, Mockito.times(1)).save(any(MedicalRecord.class));
    }

    @Test
    public void findKnownMedicalRecordByNameTest() {
        when(medicalRecordDAO.findById(any(String[].class))).thenReturn(new MedicalRecord());
        assertEquals(new MedicalRecord(), medicalRecordService.findByName("toto_test"));
        verify(medicalRecordDAO, Mockito.times(1)).findById(any(String[].class));
    }

    @Test
    public void findUnknownMedicalRecordByNameTest() {
        when(medicalRecordDAO.findById(any(String[].class))).thenReturn(null);
        assertNull(medicalRecordService.findByName("toto_test"));
        verify(medicalRecordDAO, Mockito.times(1)).findById(any(String[].class));
    }

    @Test
    void findInvalidNameTest() {
        assertThrows(InvalidFormattedFullNameException.class, () -> medicalRecordService.findByName("_"));
        verify(medicalRecordDAO, Mockito.times(0)).findById(any(String[].class));
    }

    @Test
    public void updateKnownMedicalRecordTest() {
        when(medicalRecordDAO.findById(any(String[].class))).thenReturn(new MedicalRecord());
        assertEquals(new MedicalRecord(), medicalRecordService.update(new MedicalRecord()));
        verify(medicalRecordDAO, Mockito.times(1)).findById(any(String[].class));
    }

    @Test
    public void updateUnknownMedicalRecordTest() {
        when(medicalRecordDAO.findById(any(String[].class))).thenReturn(null);
        assertThrows(MedicalRecordNotFoundException.class, () -> medicalRecordService.update(new MedicalRecord()));
        verify(medicalRecordDAO, Mockito.times(1)).findById(any(String[].class));
    }

    @Test
    public void deleteKnownMedicalRecordTest() {
        when(medicalRecordDAO.findById(any(String[].class))).thenReturn(new MedicalRecord());
        medicalRecordService.delete("toto_test");
        verify(medicalRecordDAO, Mockito.times(1)).findById(any(String[].class));
        verify(medicalRecordDAO, Mockito.times(1)).deleteById(any(String[].class));
    }

    @Test
    public void deleteUnknownMedicalRecordTest() {
        when(medicalRecordDAO.findById(any(String[].class))).thenReturn(null);
        assertThrows(MedicalRecordNotFoundException.class, () -> medicalRecordService.delete("toto_test"));
        verify(medicalRecordDAO, Mockito.times(1)).findById(any(String[].class));
        verify(medicalRecordDAO, Mockito.times(0)).deleteById(any(String[].class));
    }
}