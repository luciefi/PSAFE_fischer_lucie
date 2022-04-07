package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.dao.IFireStationDAO;
import com.openclassrooms.safetyNet.dao.IMedicalRecordDAO;
import com.openclassrooms.safetyNet.dao.IPersonDAO;
import com.openclassrooms.safetyNet.exceptions.FireStationNotFoundException;
import com.openclassrooms.safetyNet.model.*;
import com.openclassrooms.safetyNet.utils.PersonConverter;
import com.openclassrooms.safetyNet.utils.MedicalRecordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusinessService implements IBusinessService {

    private static final Logger logger = LogManager.getLogger(BusinessService.class);

    private final IPersonDAO personDAO;
    private final IFireStationDAO fireStationDAO;
    private final IMedicalRecordDAO medicalRecordDAO;

    @Autowired
    private final IMedicalRecordService medicalRecordService;

    @Autowired
    public BusinessService(IPersonDAO personDAO, IFireStationDAO fireStationDAO,
                           IMedicalRecordDAO medicalRecordDAO, IMedicalRecordService medicalRecordService) {
        this.personDAO = personDAO;
        this.fireStationDAO = fireStationDAO;
        this.medicalRecordDAO = medicalRecordDAO;
        this.medicalRecordService = medicalRecordService;
    }

    @Override
    public PersonListingForFireStation getPersonListingForFireStation(int stationNumber) {

        logger.info("Fetching every person for fire station {}", stationNumber);

        List<String> addressesForStation = fireStationDAO.getAddressesForStation(stationNumber);

        PersonListingForFireStation personListingForFireStation = new PersonListingForFireStation();

        int nbOfAdult = 0;
        int nbOfChildren = 0;

        List<Person> personList = personDAO.findByAddresses(addressesForStation);

        for (Person person : personList) {
            personListingForFireStation.getPersonsListForFireStation().add(PersonConverter.convertToLightweight(person));
            MedicalRecord medicalRecord = medicalRecordDAO.findByFirstAndLastNames(person.getFirstName(), person.getLastName());
            if (MedicalRecordUtils.isAChild(medicalRecord)) {
                nbOfChildren++;
            } else {
                nbOfAdult++;
            }
        }

        personListingForFireStation.setNumberOfAdult(nbOfAdult);
        personListingForFireStation.setNumberOfChildren(nbOfChildren);

        return personListingForFireStation;
    }

    @Override
    public List<Child> getChildren(String address) {
        logger.info("Fetching every child for address {}", address);

        List<Person> householdList = personDAO.findByAddress(address);

        List<Child> children = new ArrayList<>();

        for (Person person : householdList) {
            MedicalRecord medicalRecord =
                    medicalRecordService.findByFirstAndLastNamesOrThrow(person.getFirstName(),
                            person.getLastName());
            int age = MedicalRecordUtils.getPersonAge(medicalRecord);
            if (MedicalRecordUtils.isAChild(age)) {
                children.add(PersonConverter.convertToChild(person, age, householdList));
            }
        }
        return children;
    }

    @Override
    public List<String> getPhoneNumbers(int stationNumber) {
        logger.info("Fetching phone numbers for fire station {}", stationNumber);

        List<String> addressesForStation = fireStationDAO.getAddressesForStation(stationNumber);
        return personDAO.findByAddresses(addressesForStation)
                .stream()
                .map(Person::getPhone)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public PersonListingForAddress getPersonListingForAddress(String address) {
        logger.info("Fetching every person and fire station number for address {}", address);

        List<PersonWithMedicalRecord> personWithMedicalRecordList = personDAO.findByAddress(address)
                .stream()
                .map(person -> {
                    MedicalRecord medicalRecord =
                            medicalRecordService.findByFirstAndLastNamesOrThrow(person.getFirstName(), person.getLastName());
                    return PersonConverter.convertToPersonWithMedicalRecord(person, medicalRecord);
                })
                .collect(Collectors.toList());

        Optional<Integer> stationNumber = fireStationDAO.findByAddressAndMapToStation(address);
        if (!(stationNumber.isPresent())) {
            throw new FireStationNotFoundException();
        }

        PersonListingForAddress personListingForAddress = new PersonListingForAddress();
        personListingForAddress.setFireStation(stationNumber.get());
        personListingForAddress.setPersonsListForAddress(personWithMedicalRecordList);
        return personListingForAddress;
    }

    @Override
    public List<String> getEmails(String city) {
        return personDAO.findByCity(city).stream().map(Person::getEmail).distinct().sorted()
                .collect(Collectors.toList());
    }

    @Override
    public PersonInfo getPersonInfo(String firstName, String lastName) {
        MedicalRecord medicalRecord = medicalRecordService.findByFirstAndLastNamesOrThrow(firstName, lastName);
        return PersonConverter.convertToPersonInfo(personDAO.findByFirstAndLastNames(firstName, lastName), medicalRecord);
    }

    @Override
    public List<StationWithAddressAndPersonList> getPersonsForListOfStations(List<Integer> stations) {
        List<StationWithAddressAndPersonList> stationList = new ArrayList<>();
        stations.forEach(station -> {
            StationWithAddressAndPersonList stationWithAddressAndPerson =
                    new StationWithAddressAndPersonList();
            stationWithAddressAndPerson.setStation(station);
            stationWithAddressAndPerson.setAddressWithPersonLists(getPersonsForSingleStation(station));
            stationList.add(stationWithAddressAndPerson);

        });
        return stationList;
    }

    private List<AddressWithPersonList> getPersonsForSingleStation(Integer station) {
        List<String> addresses = fireStationDAO.getAddressesForStation(station);
        List<AddressWithPersonList> addressPersonMap = new ArrayList<>();
        addresses.forEach(address -> {
            AddressWithPersonList addressWithPersonList = new AddressWithPersonList();
            addressWithPersonList.setAddress(address);
            List<PersonWithMedicalRecord> personList =
                    personDAO.findByAddress(address).stream().map(person -> {
                        MedicalRecord medicalRecord =
                                medicalRecordService.findByFirstAndLastNamesOrThrow(person.getFirstName(), person.getLastName());
                        return PersonConverter.convertToPersonWithMedicalRecord(person, medicalRecord);
                    }).collect(Collectors.toList());
            addressWithPersonList.setAddressWithPersonLists(personList);
            addressPersonMap.add(addressWithPersonList);
        });
        return addressPersonMap;
    }
}