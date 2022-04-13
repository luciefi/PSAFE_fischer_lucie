package com.openclassrooms.safetyNet.dao;

import com.openclassrooms.safetyNet.DataSource;
import com.openclassrooms.safetyNet.model.FireStation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FireStationDAOTest {

    @Mock
    DataSource dataSource;

    FireStationDAO fireStationDAO;

    @BeforeEach
    void setUp() {
        fireStationDAO = new FireStationDAO(dataSource);
    }

    @Test
    public void getFireStationsTest() {
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation());
        when(dataSource.getFirestations()).thenReturn(fireStations);
        assertEquals(fireStations, fireStationDAO.findAll());
        verify(dataSource, Mockito.times(1)).getFirestations();
    }

    @Test
    public void saveNewFireStationMappingTest() {
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation());
        when(dataSource.getFirestations()).thenReturn(fireStations);
        fireStationDAO.save(new FireStation());
        assertEquals(2, fireStations.size());
        verify(dataSource, Mockito.times(1)).getFirestations();
    }

    @Test
    public void findKnownFireStationMappingByAddressTest() {
        List<FireStation> fireStations = new ArrayList<>();
        FireStation totoTest = new FireStation();
        totoTest.setAddress("1 Culver St");
        fireStations.add(totoTest);
        when(dataSource.getFirestations()).thenReturn(fireStations);
        assertNotNull(fireStationDAO.findByAddress("1 Culver St"));
        verify(dataSource, Mockito.times(1)).getFirestations();
    }

    @Test
    public void findUnknownFireStationMappingByAddressTest() {
        List<FireStation> fireStations = new ArrayList<>();
        FireStation totoTest = new FireStation();
        totoTest.setAddress("1 Culver St");
        fireStations.add(totoTest);
        when(dataSource.getFirestations()).thenReturn(fireStations);
        assertNull(fireStationDAO.findByAddress("2 Culver St"));
        verify(dataSource, Mockito.times(1)).getFirestations();
    }

    @Test
    public void deleteFireStationMappingTest() {
        List<FireStation> fireStations = new ArrayList<>();
        FireStation totoTest = new FireStation();
        totoTest.setAddress("1 Culver St");
        fireStations.add(totoTest);
        when(dataSource.getFirestations()).thenReturn(fireStations);
        fireStationDAO.deleteByAddress("1 Culver St");
        assertEquals(0, fireStations.size());
    }

    @Test
    public void getAddressesForStationTest() {
        // Arrange
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation("address 1", 1));
        fireStations.add(new FireStation("address 2", 2));
        fireStations.add(new FireStation("address 3", 2));
        when(dataSource.getFirestations()).thenReturn(fireStations);
        // Act
        List<String> addresses = fireStationDAO.getAddressesForStation(2);
        // Assert
        verify(dataSource, Mockito.times(1)).getFirestations();
        assertEquals(2, addresses.size());
    }

    @Test
    public void getStationForAddressTest(){
        // Arrange
        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(new FireStation("address 1", 1));
        fireStations.add(new FireStation("address 2", 2));
        fireStations.add(new FireStation("address 3", 2));
        when(dataSource.getFirestations()).thenReturn(fireStations);

        // Act
        Optional<Integer> station = fireStationDAO.findByAddressAndMapToStation("address 2");

        // Assert
        verify(dataSource, Mockito.times(1)).getFirestations();
        assertTrue(station.isPresent());
        assertEquals(2, station.get());
    }
}