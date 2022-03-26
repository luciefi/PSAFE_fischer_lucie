package com.openclassrooms.safetyNet.dao;

import com.openclassrooms.safetyNet.model.FireStation;

import java.util.List;
import java.util.Optional;

public interface IFireStationDAO {
    List<FireStation> findAll();

    FireStation save(FireStation newFireStation);

    FireStation findById(String address);

    void deleteById(String address);

    List<String> getAddressesForStation(int stationNumber);

    Optional<Integer> getStationForAddress(String address);
}