package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.dao.IFireStationDAO;
import com.openclassrooms.safetyNet.dao.IMedicalRecordDAO;
import com.openclassrooms.safetyNet.dao.IPersonDAO;
import com.openclassrooms.safetyNet.model.Child;
import com.openclassrooms.safetyNet.model.Person;
import com.openclassrooms.safetyNet.model.PersonListingForFireStation;
import com.openclassrooms.safetyNet.utils.PersonConverter;
import com.openclassrooms.safetyNet.utils.PersonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class BusinessService implements IBusinessService {

    private static final Logger logger = LogManager.getLogger(FireStationService.class);

    private final IPersonDAO personDAO;
    private final IFireStationDAO fireStationDAO;
    private final IMedicalRecordDAO medicalRecordDAO;

    @Autowired
    public BusinessService(IPersonDAO personDAO, IFireStationDAO fireStationDAO, IMedicalRecordDAO medicalRecordDAO) {
        this.personDAO = personDAO;
        this.fireStationDAO = fireStationDAO;
        this.medicalRecordDAO = medicalRecordDAO;
    }


    @Override
    public PersonListingForFireStation getPersonListingForFireStation(int stationNumber) {

        logger.info("Fetching every person for fire station {}", stationNumber);

        List<String> addressesForStation = getAddressesForStation(stationNumber);

        PersonListingForFireStation personListingForFireStation = new PersonListingForFireStation();

        AtomicInteger nbOfAdult = new AtomicInteger();
        AtomicInteger nbOfChildren = new AtomicInteger();

        List<Person> personList = getPersonForAddresses(addressesForStation);

        for (Person person : personList) {
            personListingForFireStation.getPersonsListForFireStation().add(PersonConverter.convertToLightweight(person));
            if (PersonUtils.isAChild(person.getFirstName(), person.getLastName(), medicalRecordDAO.findAll())) {
                nbOfChildren.getAndIncrement();
            } else {
                nbOfAdult.getAndIncrement();
            }
        }

        personListingForFireStation.setNumberOfAdult(nbOfAdult.get());
        personListingForFireStation.setNumberOfChildren(nbOfChildren.get());

        return personListingForFireStation;
    }

    @Override
    public List<Child> getChildren(String address) {
        List<Person> householdList = personDAO.findAll()
                .stream()
                .filter(person -> address.equals(person.getAddress()))
                .collect(Collectors.toList());

        List<Child> children = new ArrayList<>();

        for (Person person : householdList) {
            int age = PersonUtils.getPersonAge(person.getFirstName(), person.getLastName(), medicalRecordDAO.findAll());
            if (age < 18) {
                children.add(createChildren(person, age, householdList));
            }
        }

        return children;
    }

    @Override
    public List<String> getPhoneNumbers(int stationNumber) {
        List<String> addressesForStation = getAddressesForStation(stationNumber);
        return personDAO.findAll()
                .stream()
                .filter(person -> addressesForStation.contains(person.getAddress()))
                .map(person -> person.getPhone())
                .distinct()
                .collect(Collectors.toList());
    }

    private Child createChildren(Person person, int age, List<Person> householdList) {
        List<Person> filteredHousehold = householdList.stream().filter(p -> !(p.getFirstName().equals(person.getFirstName()) && p.getLastName().equals(person.getLastName()))).collect(Collectors.toList());
        return new Child(person.getFirstName(), person.getLastName(), age, filteredHousehold);
    }

    private List<String> getAddressesForStation(int stationNumber) {
        return fireStationDAO.findAll()
                .stream()
                .filter(fireStation -> fireStation.getStation() == stationNumber)
                .map(fireStation -> fireStation.getAddress())
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Person> getPersonForAddresses(List addresses) {
        return personDAO.findAll()
                .stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .collect(Collectors.toList());
    }
}