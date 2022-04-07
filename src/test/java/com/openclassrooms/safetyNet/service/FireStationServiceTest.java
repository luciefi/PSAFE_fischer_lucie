package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.dao.IFireStationDAO;
import com.openclassrooms.safetyNet.exceptions.FireStationAlreadyExistsException;
import com.openclassrooms.safetyNet.exceptions.FireStationNotFoundException;
import com.openclassrooms.safetyNet.model.FireStation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    @Mock
    private IFireStationDAO fireStationDAO;

    @InjectMocks
    private FireStationService fireStationService;

    @Test
    public void getAllFireStationTest() {
        when(fireStationDAO.findAll()).thenReturn(Collections.singletonList(new FireStation()));
        assertEquals(1, fireStationService.getAll().size());
        verify(fireStationDAO, Mockito.times(1)).findAll();
    }

    @Test
    public void saveAlreadyExistingFireStationMappingTest() {
        FireStation fireStation = new FireStation();
        fireStation.setStation(1);
        fireStation.setAddress("1 Culver St");
        when(fireStationDAO.findByAddress(anyString())).thenReturn(new FireStation());
        assertThrows(FireStationAlreadyExistsException.class, () -> fireStationService.save(fireStation));
        verify(fireStationDAO, Mockito.times(0)).save(any(FireStation.class));
        verify(fireStationDAO, Mockito.times(1)).findByAddress(anyString());
    }

    @Test
    public void saveNewFireStationMappingTest() {
        FireStation fireStation = new FireStation();
        fireStation.setStation(1);
        fireStation.setAddress("1 Culver St");
        when(fireStationDAO.save(any(FireStation.class))).thenReturn(new FireStation());
        when(fireStationDAO.findByAddress(anyString())).thenReturn(null);
        fireStationService.save(fireStation);
        verify(fireStationDAO, Mockito.times(1)).findByAddress(anyString());
        verify(fireStationDAO, Mockito.times(1)).save(any(FireStation.class));
    }

    @Test
    public void findKnownFireStationMappingByAddressTest() {
        when(fireStationDAO.findByAddress(anyString())).thenReturn(new FireStation());
        assertEquals(new FireStation(), fireStationService.findByAddress("toto_test"));
        verify(fireStationDAO, Mockito.times(1)).findByAddress(anyString());
    }

    @Test
    public void findUnknownFireStationMappingByAddressTest() {
        when(fireStationDAO.findByAddress(anyString())).thenReturn(null);
        assertNull(fireStationService.findByAddress("toto_test"));
        verify(fireStationDAO, Mockito.times(1)).findByAddress(anyString());
    }

    @Test
    public void updateKnownFireStationMappingTest() {
        FireStation fireStation = new FireStation();
        fireStation.setStation(1);
        fireStation.setAddress("1 Culver St");
        when(fireStationDAO.findByAddress(anyString())).thenReturn(new FireStation());
        assertEquals(fireStation, fireStationService.update(fireStation));
        verify(fireStationDAO, Mockito.times(1)).findByAddress(anyString());
    }

    @Test
    public void updateUnknownFireStationMappingTest() {
        when(fireStationDAO.findByAddress(anyString())).thenReturn(null);
        FireStation fireStation = new FireStation();
        fireStation.setStation(1);
        fireStation.setAddress("1 Culver St");

        assertThrows(FireStationNotFoundException.class, () -> fireStationService.update(fireStation));
        verify(fireStationDAO, Mockito.times(1)).findByAddress(anyString());
    }

    @Test
    public void deleteKnownFireStationMappingTest() {
        when(fireStationDAO.findByAddress(anyString())).thenReturn(new FireStation());
        fireStationService.delete("toto_test");
        verify(fireStationDAO, Mockito.times(1)).findByAddress(anyString());
        verify(fireStationDAO, Mockito.times(1)).deleteByAddress(anyString());
    }

    @Test
    public void deleteUnknownFireStationMappingTest() {
        when(fireStationDAO.findByAddress(anyString())).thenReturn(null);
        assertThrows(FireStationNotFoundException.class, () -> fireStationService.delete("toto_test"));
        verify(fireStationDAO, Mockito.times(1)).findByAddress(anyString());
        verify(fireStationDAO, Mockito.times(0)).deleteByAddress(anyString());
    }
}