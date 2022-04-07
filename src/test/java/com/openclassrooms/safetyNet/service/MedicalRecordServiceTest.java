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

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
        when(medicalRecordDAO.findByFirstAndLastNames(anyString(), anyString())).thenReturn(new MedicalRecord());
        assertThrows(MedicalRecordAlreadyExistsException.class, () -> medicalRecordService.save(new MedicalRecord()));
        verify(medicalRecordDAO, Mockito.times(0)).save(any(MedicalRecord.class));
        verify(medicalRecordDAO, Mockito.times(1)).findByFirstAndLastNames(anyString(), anyString());
    }

    @Test
    public void saveNewMedicalRecordTest() {
        when(medicalRecordDAO.save(any(MedicalRecord.class))).thenReturn(new MedicalRecord());
        when(medicalRecordDAO.findByFirstAndLastNames(anyString(), anyString())).thenReturn(null);
        medicalRecordService.save(new MedicalRecord());
        verify(medicalRecordDAO, Mockito.times(1)).findByFirstAndLastNames(anyString(), anyString());
        verify(medicalRecordDAO, Mockito.times(1)).save(any(MedicalRecord.class));
    }

    @Test
    public void findKnownMedicalRecordByNameTest() {
        when(medicalRecordDAO.findByFirstAndLastNames(anyString(), anyString())).thenReturn(new MedicalRecord());
        assertEquals(new MedicalRecord(), medicalRecordService.findByName("toto_test"));
        verify(medicalRecordDAO, Mockito.times(1)).findByFirstAndLastNames(anyString(), anyString());
    }

    @Test
    public void findUnknownMedicalRecordByNameTest() {
        when(medicalRecordDAO.findByFirstAndLastNames(anyString(), anyString())).thenReturn(null);
        assertNull(medicalRecordService.findByName("toto_test"));
        verify(medicalRecordDAO, Mockito.times(1)).findByFirstAndLastNames(anyString(), anyString());
    }


    @Test
    void findUnknownMedicalRecordByNameAndThrowTest() {
        when(medicalRecordDAO.findByFirstAndLastNames(anyString(), anyString())).thenReturn(null);
        assertThrows(MedicalRecordNotFoundException.class,
                () -> medicalRecordService.findByFirstAndLastNamesOrThrow(
                        "toto", "test"));
        verify(medicalRecordDAO, Mockito.times(1)).findByFirstAndLastNames(anyString(), anyString());
    }

    @Test
    void findInvalidNameTest() {
        assertThrows(InvalidFormattedFullNameException.class, () -> medicalRecordService.findByName("_"));
        verify(medicalRecordDAO, Mockito.times(0)).findByFirstAndLastNames(anyString(), anyString());
    }

    @Test
    public void updateKnownMedicalRecordTest() {
        when(medicalRecordDAO.findByFirstAndLastNames(anyString(), anyString())).thenReturn(new MedicalRecord());
        assertEquals(new MedicalRecord(), medicalRecordService.update(new MedicalRecord()));
        verify(medicalRecordDAO, Mockito.times(1)).findByFirstAndLastNames(anyString(), anyString());
    }

    @Test
    public void updateUnknownMedicalRecordTest() {
        when(medicalRecordDAO.findByFirstAndLastNames(anyString(), anyString())).thenReturn(null);
        assertThrows(MedicalRecordNotFoundException.class, () -> medicalRecordService.update(new MedicalRecord()));
        verify(medicalRecordDAO, Mockito.times(1)).findByFirstAndLastNames(anyString(), anyString());
    }

    @Test
    public void deleteKnownMedicalRecordTest() {
        when(medicalRecordDAO.findByFirstAndLastNames(anyString(), anyString())).thenReturn(new MedicalRecord());
        medicalRecordService.delete("toto_test");
        verify(medicalRecordDAO, Mockito.times(1)).findByFirstAndLastNames(anyString(), anyString());
        verify(medicalRecordDAO, Mockito.times(1)).deleteByFirstAndLastNames(anyString(), anyString());
    }

    @Test
    public void deleteUnknownMedicalRecordTest() {
        when(medicalRecordDAO.findByFirstAndLastNames(anyString(), anyString())).thenReturn(null);
        assertThrows(MedicalRecordNotFoundException.class, () -> medicalRecordService.delete("toto_test"));
        verify(medicalRecordDAO, Mockito.times(1)).findByFirstAndLastNames(anyString(), anyString());
        verify(medicalRecordDAO, Mockito.times(0)).deleteByFirstAndLastNames(anyString(), anyString());
    }
}