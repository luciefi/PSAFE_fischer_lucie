package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.dto.*;
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
    public PersonListingForFireStationDTO getPersonListingForFireStation(int stationNumber) {

        logger.info("Fetching every person for fire station {}", stationNumber);

        List<String> addressesForStation = fireStationDAO.getAddressesForStation(stationNumber);

        PersonListingForFireStationDTO personListingForFireStation = new PersonListingForFireStationDTO();

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
    public List<ChildDTO> getChildren(String address) {
        logger.info("Fetching every child for address {}", address);

        List<Person> householdList = personDAO.findByAddress(address);

        List<ChildDTO> children = new ArrayList<>();

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
    public PersonListingForAddressDTO getPersonListingForAddress(String address) {
        logger.info("Fetching every person and fire station number for address {}", address);

        List<PersonWithMedicalRecordDTO> personWithMedicalRecordList = personDAO.findByAddress(address)
                .stream()
                .map(person -> {
                    MedicalRecord medicalRecord =
                            medicalRecordService.findByFirstAndLastNamesOrThrow(person.getFirstName(), person.getLastName());
                    return PersonConverter.convertToPersonWithMedicalRecord(person, medicalRecord);
                })
                .collect(Collectors.toList());

        Optional<Integer> stationNumber = fireStationDAO.findByAddressAndMapToStation(address);
        if (!(stationNumber.isPresent())) {
            logger.error("Fire station for address {}  not found.", address);
            throw new FireStationNotFoundException();
        }

        PersonListingForAddressDTO personListingForAddress = new PersonListingForAddressDTO();
        personListingForAddress.setFireStation(stationNumber.get());
        personListingForAddress.setPersonsListForAddress(personWithMedicalRecordList);
        return personListingForAddress;
    }

    @Override
    public List<String> getEmails(String city) {
        logger.info("Fetching every email address for city {}", city);

        return personDAO.findByCity(city).stream().map(Person::getEmail).distinct().sorted()
                .collect(Collectors.toList());
    }

    @Override
    public PersonInfoDTO getPersonInfo(String firstName, String lastName) {
        logger.info("Fetching person information for {} {}", firstName, lastName);

        MedicalRecord medicalRecord = medicalRecordService.findByFirstAndLastNamesOrThrow(firstName, lastName);
        return PersonConverter.convertToPersonInfo(personDAO.findByFirstAndLastNames(firstName, lastName), medicalRecord);
    }

    @Override
    public List<StationWithAddressListingDTO> getPersonsForListOfStations(List<Integer> stations) {
        logger.info("Fetching persons and addresses for stations {}", stations.toString());

        List<StationWithAddressListingDTO> stationList = new ArrayList<>();
        stations.forEach(station -> {
            StationWithAddressListingDTO stationWithAddressAndPerson =
                    new StationWithAddressListingDTO();
            stationWithAddressAndPerson.setStation(station);
            stationWithAddressAndPerson.setAddressListing(getPersonsForSingleStation(station));
            stationList.add(stationWithAddressAndPerson);

        });
        return stationList;
    }

    private List<AddressWithPersonListingDTO> getPersonsForSingleStation(Integer station) {
        List<String> addresses = fireStationDAO.getAddressesForStation(station);
        List<AddressWithPersonListingDTO> addressPersonMap = new ArrayList<>();
        addresses.forEach(address -> {
            AddressWithPersonListingDTO addressWithPersonListing = new AddressWithPersonListingDTO();
            addressWithPersonListing.setAddress(address);
            List<PersonWithMedicalRecordDTO> personList =
                    personDAO.findByAddress(address).stream().map(person -> {
                        MedicalRecord medicalRecord =
                                medicalRecordService.findByFirstAndLastNamesOrThrow(person.getFirstName(), person.getLastName());
                        return PersonConverter.convertToPersonWithMedicalRecord(person, medicalRecord);
                    }).collect(Collectors.toList());
            addressWithPersonListing.setPersonListing(personList);
            addressPersonMap.add(addressWithPersonListing);
        });
        return addressPersonMap;
    }
}