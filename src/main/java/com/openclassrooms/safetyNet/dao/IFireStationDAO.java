package com.openclassrooms.safetyNet.dao;

import com.openclassrooms.safetyNet.model.FireStation;

import java.util.List;

public interface IFireStationDAO {
    List<FireStation> findAll();

    FireStation save(FireStation newFireStation);

    FireStation findById(String address);

    void deleteById(String address);
}
