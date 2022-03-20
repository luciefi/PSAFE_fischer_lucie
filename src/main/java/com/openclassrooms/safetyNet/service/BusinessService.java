package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.dao.IFireStationDAO;
import com.openclassrooms.safetyNet.dao.IMedicalRecordDAO;
import com.openclassrooms.safetyNet.dao.IPersonDAO;
import com.openclassrooms.safetyNet.model.Person;
import com.openclassrooms.safetyNet.model.PersonListingForFireStation;
import com.openclassrooms.safetyNet.utils.PersonConverter;
import com.openclassrooms.safetyNet.utils.PersonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        List<String> addressesForStation = fireStationDAO.findAll()
                .stream()
                .filter(fireStation -> fireStation.getStation() == stationNumber)
                .map(fireStation -> fireStation.getAddress())
                .distinct()
                .collect(Collectors.toList());

        PersonListingForFireStation personListingForFireStation = new PersonListingForFireStation();

        AtomicInteger nbOfAdult = new AtomicInteger();
        AtomicInteger nbOfChildren = new AtomicInteger();

        List<Person> personList = personDAO.findAll()
                .stream()
                .filter(person -> addressesForStation.contains(person.getAddress()))
                .collect(Collectors.toList());


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
}
