package com.openclassrooms.safetyNet.dao;

import com.openclassrooms.safetyNet.DataSource;
import com.openclassrooms.safetyNet.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FireStationDAO implements IFireStationDAO {

    private final DataSource dataSource;

    @Autowired
    public FireStationDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<FireStation> findAll() {
        return dataSource.getFirestations();
    }

    @Override
    public FireStation save(FireStation newFireStation) {
        List<FireStation> fireStationList = dataSource.getFirestations();
        fireStationList.add(newFireStation);
        return newFireStation;
    }

    @Override
    public FireStation findByAddress(String address) {
        List<FireStation> fireStationList = dataSource.getFirestations();
        List<FireStation> foundFireStationList = fireStationList.stream().filter(fireStation -> fireStation.getAddress().equals(address)).collect(Collectors.toList());
        if (foundFireStationList.size() > 0) {
            return foundFireStationList.get(0);
        }
        return null;
    }

    @Override
    public void deleteByAddress(String address) {
        dataSource.getFirestations().remove(findByAddress(address));
    }

    @Override
    public List<String> getAddressesForStation(int stationNumber) {
        return findAll()
                .stream()
                .filter(fireStation -> fireStation.getStation() == stationNumber)
                .map(FireStation::getAddress)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Integer> findByAddressAndMapToStation(String address) {
        return findAll()
                .stream()
                .filter(fireStation -> Objects.equals(fireStation.getAddress(), address))
                .map(FireStation::getStation)
                .findFirst();
    }
}