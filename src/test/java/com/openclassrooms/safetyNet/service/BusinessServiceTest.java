package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.dao.IFireStationDAO;
import com.openclassrooms.safetyNet.dao.IMedicalRecordDAO;
import com.openclassrooms.safetyNet.dao.IPersonDAO;
import com.openclassrooms.safetyNet.exceptions.FireStationNotFoundException;
import com.openclassrooms.safetyNet.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BusinessServiceTest {

    @Mock
    private IPersonDAO personDAO;

    @Mock
    private IFireStationDAO fireStationDAO;

    @Mock
    private IMedicalRecordDAO medicalRecordDAO;

    @Mock
    IMedicalRecordService medicalRecordService;

    @InjectMocks
    private BusinessService businessService;

    @Test
    public void getPersonListingForFireStationTest() throws ParseException {
        // Arrange
        List<String> addresses = new ArrayList<>();
        addresses.add("address 1");
        addresses.add("address 2");
        when(fireStationDAO.getAddressesForStation(anyInt())).thenReturn(addresses);

        List<Person> personList = new ArrayList<>();
        Person person = new Person();
        person.setFirstName("");
        person.setLastName("");
        personList.add(person);
        personList.add(person);
        personList.add(person);
        when(personDAO.findByAddresses(any(List.class))).thenReturn(personList);

        MedicalRecord childMedicalRecord = new MedicalRecord();
        childMedicalRecord.setBirthdate(new Date());

        MedicalRecord adultMedicalRecord = new MedicalRecord();
        String date_string = "01/01/1989";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date adultBirthDate = formatter.parse(date_string);
        adultMedicalRecord.setBirthdate(adultBirthDate);
        when(medicalRecordDAO.findByFirstAndLastNames(anyString(), anyString())).thenReturn(childMedicalRecord).thenReturn(adultMedicalRecord);

        // Act
        PersonListingForFireStation personListingForFireStation = businessService.getPersonListingForFireStation(1);

        // Assert
        verify(fireStationDAO, Mockito.times(1)).getAddressesForStation(anyInt());
        verify(personDAO, Mockito.times(1)).findByAddresses(any(List.class));
        verify(medicalRecordDAO, Mockito.times(3)).findByFirstAndLastNames(anyString(), anyString());
        Assertions.assertEquals(2, personListingForFireStation.getNumberOfAdult());
        Assertions.assertEquals(1, personListingForFireStation.getNumberOfChildren());
    }

    @Test
    public void getChildrenTest() throws ParseException {
        // Arrange
        List<Person> personList = new ArrayList<>();
        Person person = new Person();
        person.setFirstName("Toto");
        person.setLastName("Test");
        personList.add(person);
        Person person2 = new Person();
        person2.setFirstName("Jojo");
        person2.setLastName("Test");
        personList.add(person2);
        Person person3 = new Person();
        person3.setFirstName("Toto");
        person3.setLastName("TEST");
        personList.add(person3);
        when(personDAO.findByAddress(anyString())).thenReturn(personList);

        MedicalRecord childMedicalRecord = new MedicalRecord();
        childMedicalRecord.setBirthdate(new Date());

        MedicalRecord adultMedicalRecord = new MedicalRecord();
        String date_string = "01/01/1989";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date adultBirthDate = formatter.parse(date_string);
        adultMedicalRecord.setBirthdate(adultBirthDate);
        when(medicalRecordService.findByFirstAndLastNamesOrThrow(anyString(), anyString())).thenReturn(childMedicalRecord).thenReturn(adultMedicalRecord);

        // Act
        List<Child> children = businessService.getChildren("address 1");

        // Assert
        verify(personDAO, Mockito.times(1)).findByAddress(anyString());
        verify(medicalRecordService, Mockito.times(3)).findByFirstAndLastNamesOrThrow(anyString(), anyString());
        Assertions.assertEquals(1, children.size());
        Assertions.assertEquals(2, children.get(0).getHouseholdMembers().size());
    }

    @Test
    public void getPhoneNumbersTest() {
        // Arrange
        List<Person> personList = new ArrayList<>();
        Person person = new Person();
        person.setPhone("123");
        personList.add(person);
        Person person2 = new Person();
        person2.setPhone("456");
        personList.add(person2);
        personList.add(person2);
        when(personDAO.findByAddresses(any(List.class))).thenReturn(personList);
        when(fireStationDAO.getAddressesForStation(anyInt())).thenReturn(new ArrayList<>());

        // Act
        List<String> phoneNumbers = businessService.getPhoneNumbers(1);

        // Assert
        verify(fireStationDAO, Mockito.times(1)).getAddressesForStation(anyInt());
        verify(personDAO, Mockito.times(1)).findByAddresses(any(List.class));
        Assertions.assertEquals(2, phoneNumbers.size());
    }

    @Test
    public void getPersonListingForAddressTest() {
        // Arrange
        when(fireStationDAO.findByAddressAndMapToStation(anyString())).thenReturn(Optional.of(2));

        List<Person> personList = new ArrayList<>();
        personList.add((new Person("a", "A", "", "")));
        personList.add((new Person("b", "B", "", "")));
        personList.add((new Person("c", "C", "", "")));
        when(personDAO.findByAddress(anyString())).thenReturn(personList);

        MedicalRecord medicalRecord = new MedicalRecord("", "", new Date(), new ArrayList<>(), new ArrayList<>());
        when(medicalRecordService.findByFirstAndLastNamesOrThrow(anyString(), anyString())).thenReturn(medicalRecord);

        // Act
        PersonListingForAddress personListingForAddress = businessService.getPersonListingForAddress("my address");

        // Assert
        verify(fireStationDAO, Mockito.times(1)).findByAddressAndMapToStation(anyString());
        verify(personDAO, Mockito.times(1)).findByAddress(anyString());
        verify(medicalRecordService, Mockito.times(3)).findByFirstAndLastNamesOrThrow(anyString(), anyString());
        Assertions.assertEquals(2, personListingForAddress.getFireStation());
        Assertions.assertEquals(3, personListingForAddress.getPersonsListForAddress().size());
    }

    @Test
    public void getPersonListingForAddressFireStationNotFoundTest() {
        // Arrange
        when(fireStationDAO.findByAddressAndMapToStation(anyString())).thenReturn(Optional.empty());
        when(personDAO.findByAddress(anyString())).thenReturn(new ArrayList<>());

        // Act - Assert
        assertThrows(FireStationNotFoundException.class, () -> businessService.getPersonListingForAddress("my address"));

        // Assert
        verify(fireStationDAO, Mockito.times(1)).findByAddressAndMapToStation(anyString());
        verify(personDAO, Mockito.times(1)).findByAddress(anyString());
        verify(medicalRecordService, Mockito.times(0)).findByFirstAndLastNamesOrThrow(anyString(), anyString());
    }

    @Test
    public void getEmailsTest() {
        // Arrange
        List<Person> personList = new ArrayList<>();
        Person person = new Person();
        person.setEmail("abc@de.com");
        personList.add(person);
        personList.add(person);
        Person person2 = new Person();
        person2.setEmail("def@ab.com");
        personList.add(person2);

        when(personDAO.findByCity(anyString())).thenReturn(personList);

        // Act
        List<String> emails = businessService.getEmails("my city");

        // Assert
        verify(personDAO, Mockito.times(1)).findByCity(anyString());
        Assertions.assertEquals(2, emails.size());
    }

    @Test
    public void getPersonInfoTest() {
        // Arrange
        when(personDAO.findByFirstAndLastNames(anyString(), anyString())).thenReturn(new Person());
        MedicalRecord medicalRecord = new MedicalRecord("test", "", new Date(), new ArrayList<>(),
                new ArrayList<>());
        when(medicalRecordService.findByFirstAndLastNamesOrThrow(anyString(), anyString())).thenReturn(medicalRecord);

        // Act
        PersonInfo personInfo = businessService.getPersonInfo("", "");

        // Assert
        assertNotNull(personInfo);
        verify(medicalRecordService, Mockito.times(1)).findByFirstAndLastNamesOrThrow(anyString(), anyString());
        verify(personDAO, Mockito.times(1)).findByFirstAndLastNames(anyString(), anyString());
    }

    @Test
    public void getPersonsForListOfStations() {
        // Arrange
        List<Person> personList = new ArrayList<>();
        Person person = new Person();
        person.setFirstName("");
        person.setLastName("");
        personList.add(person);
        personList.add(person);
        when(personDAO.findByAddress(anyString())).thenReturn(personList);

        MedicalRecord medicalRecord = new MedicalRecord("", "", new Date(), new ArrayList<>(),
                new ArrayList<>());
        when(medicalRecordService.findByFirstAndLastNamesOrThrow(anyString(), anyString())).thenReturn(medicalRecord);

        List<String> addresses = new ArrayList<>();
        addresses.add("my address");
        addresses.add("my address 2");
        when(fireStationDAO.getAddressesForStation(anyInt())).thenReturn(addresses);

        List<Integer> stations = new ArrayList<>();
        stations.add(1);
        stations.add(2);

        // Act
        List<StationWithAddressAndPersonList> resultList =
                businessService.getPersonsForListOfStations(stations);

        // Assert
        assertNotNull(resultList);
        verify(personDAO, Mockito.times(4)).findByAddress(anyString());
        verify(medicalRecordService, Mockito.times(8)).findByFirstAndLastNamesOrThrow(anyString()
                , anyString());
        verify(fireStationDAO, Mockito.times(2)).getAddressesForStation(anyInt());
    }
}
