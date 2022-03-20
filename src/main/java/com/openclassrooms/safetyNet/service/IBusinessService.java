package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.model.Child;
import com.openclassrooms.safetyNet.model.PersonListingForFireStation;

import java.util.List;

public interface IBusinessService {
    PersonListingForFireStation getPersonListingForFireStation(int stationNumber);

    List<Child> getChildren(String address);
}
