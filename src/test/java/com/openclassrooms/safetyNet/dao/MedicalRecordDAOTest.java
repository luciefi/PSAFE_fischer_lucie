package com.openclassrooms.safetyNet.dao;

import com.openclassrooms.safetyNet.DataSource;
import com.openclassrooms.safetyNet.exceptions.MedicalRecordNotFoundException;
import com.openclassrooms.safetyNet.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordDAOTest {

    @Mock
    DataSource dataSource;

    MedicalRecordDAO medicalRecordDAO;

    @BeforeEach
    void setUp() {
        medicalRecordDAO = new MedicalRecordDAO(dataSource);
    }

    @Test
    void getMedicalRecordsTest() {
        ArrayList<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecord());
        when(dataSource.getMedicalrecords()).thenReturn(medicalRecords);

        assertEquals(1, medicalRecordDAO.findAll().size());
        verify(dataSource, Mockito.times(1)).getMedicalrecords();
    }

    @Test
    void saveNewMedicalRecordTest() {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(new MedicalRecord());
        when(dataSource.getMedicalrecords()).thenReturn(medicalRecords);
        medicalRecordDAO.save(new MedicalRecord());
        assertEquals(2, medicalRecords.size());
        verify(dataSource, Mockito.times(1)).getMedicalrecords();
    }

    @Test
    void findKnownMedicalRecordByNameTest() {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        MedicalRecord totoTest = new MedicalRecord();
        totoTest.setFirstName("toto");
        totoTest.setLastName("test");
        medicalRecords.add(totoTest);
        when(dataSource.getMedicalrecords()).thenReturn(medicalRecords);
        assertNotNull(medicalRecordDAO.findByIdOrThrow("toto", "test"));
        verify(dataSource, Mockito.times(1)).getMedicalrecords();
    }

    @Test
    void findUnknownMedicalRecordByNameTest() {
        when(dataSource.getMedicalrecords()).thenReturn(new ArrayList<>());
        assertNull(medicalRecordDAO.findById("toto", "test"));
        verify(dataSource, Mockito.times(1)).getMedicalrecords();
    }

    @Test
    void findFirstNameUnknownMedicalRecordByNameTest() {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        MedicalRecord totoTest = new MedicalRecord();
        totoTest.setFirstName("ttotto");
        totoTest.setLastName("test");
        medicalRecords.add(totoTest);
        when(dataSource.getMedicalrecords()).thenReturn(medicalRecords);
        assertNull(medicalRecordDAO.findById("toto", "test"));
        verify(dataSource, Mockito.times(1)).getMedicalrecords();
    }

    @Test
    void findLastNameUnknownMedicalRecordByNameTest() {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        MedicalRecord totoTest = new MedicalRecord();
        totoTest.setFirstName("toto");
        totoTest.setLastName("testTest");
        medicalRecords.add(totoTest);
        when(dataSource.getMedicalrecords()).thenReturn(medicalRecords);
        assertNull(medicalRecordDAO.findById("toto", "test"));
        verify(dataSource, Mockito.times(1)).getMedicalrecords();
    }

    @Test
    void findUnknownMedicalRecordByNameAndThrowTest() {
        when(dataSource.getMedicalrecords()).thenReturn(new ArrayList<>());
        assertThrows(MedicalRecordNotFoundException.class,
                () -> medicalRecordDAO.findByIdOrThrow(
                        "toto", "test"));
        verify(dataSource, Mockito.times(1)).getMedicalrecords();
    }

    @Test
    void deleteMedicalRecordTest() {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        MedicalRecord totoTest = new MedicalRecord();
        totoTest.setFirstName("toto");
        totoTest.setLastName("test");
        medicalRecords.add(totoTest);
        when(dataSource.getMedicalrecords()).thenReturn(medicalRecords);
        medicalRecordDAO.deleteById("toto", "test");
        assertEquals(0, medicalRecords.size());
    }
}