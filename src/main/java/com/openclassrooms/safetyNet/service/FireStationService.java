package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.dao.IFireStationDAO;
import com.openclassrooms.safetyNet.exceptions.*;
import com.openclassrooms.safetyNet.model.FireStation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationService implements IFireStationService {

    private static final Logger logger = LogManager.getLogger(FireStationService.class);

    private final IFireStationDAO fireStationDAO;

    @Autowired
    public FireStationService(IFireStationDAO fireStationDAO) {
        this.fireStationDAO = fireStationDAO;
    }

    @Override
    public List<FireStation> getAll() {
        logger.info("Fetching every fire station in the list");
        return fireStationDAO.findAll();
    }

    @Override
    public FireStation save(FireStation newFireStation) {
        logger.info("Adding fire station with address {}", newFireStation.getAddress());
        FireStation currentFireStation = findByAddress(newFireStation.getAddress());
        if (currentFireStation != null) {
            logger.error("Unable to add. A fire station for address {} already exist", newFireStation.getAddress());
            throw new FireStationAlreadyExistsException();
        }
        return fireStationDAO.save(newFireStation);
    }

    @Override
    public FireStation findByAddress(String address) {
        return fireStationDAO.findByAddress(address);
    }

    @Override
    public FireStation update(FireStation updatedFireStation) {
        logger.info("Updating fire station with address {}", updatedFireStation.getAddress());
        FireStation currentFireStation = findByAddress(updatedFireStation.getAddress());

        if (currentFireStation == null) {
            logger.error("Unable to update. Fire station with address {} not found.", updatedFireStation.getAddress());
            throw new FireStationNotFoundException();
        }
        currentFireStation.setAddress(updatedFireStation.getAddress());
        currentFireStation.setStation(updatedFireStation.getStation());
        return currentFireStation;
    }

    @Override
    public void delete(String address) {
        logger.info("Deleting fire station with name {}", address);
        FireStation currentFireStation = findByAddress(address);
        if (currentFireStation == null) {
            logger.error("Unable to delete. Fire station with address {} not found.", address);
            throw new FireStationNotFoundException();
        }
        fireStationDAO.deleteByAddress(address);
    }

}
