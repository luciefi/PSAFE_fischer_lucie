package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.model.PersonListingForFireStation;

public interface IBusinessService {
    PersonListingForFireStation getPersonListingForFireStation(int stationNumber);
}
