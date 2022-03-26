package com.openclassrooms.safetyNet.dao;

import com.openclassrooms.safetyNet.model.FireStation;
import com.openclassrooms.safetyNet.service.BusinessService;

import java.util.List;
import java.util.stream.Collectors;

public interface IFireStationDAO {
    List<FireStation> findAll();

    FireStation save(FireStation newFireStation);

    FireStation findById(String address);

    void deleteById(String address);

     List<String> getAddressesForStation(int stationNumber);
}
