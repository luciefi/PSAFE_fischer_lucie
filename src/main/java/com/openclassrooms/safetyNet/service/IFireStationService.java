package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.model.FireStation;

import java.util.List;

public interface IFireStationService {
    List<FireStation> getAll();

    FireStation save(FireStation fireStation);

    FireStation findByAddress(String address);

    void delete(String fullName);

    FireStation update(FireStation updatedFireStation);
}
