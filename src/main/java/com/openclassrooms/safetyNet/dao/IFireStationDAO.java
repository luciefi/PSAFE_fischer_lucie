package com.openclassrooms.safetyNet.dao;

import com.openclassrooms.safetyNet.model.FireStation;

import java.util.List;
import java.util.Optional;

public interface IFireStationDAO {
    List<FireStation> findAll();

    FireStation save(FireStation newFireStation);

    FireStation findByAddress(String address);

    void deleteByAddress(String address);

    List<String> getAddressesForStation(int stationNumber);

    Optional<Integer> findByAddressAndMapToStation(String address);
}