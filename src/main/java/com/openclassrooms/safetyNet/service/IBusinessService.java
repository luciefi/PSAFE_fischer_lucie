package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.model.Child;
import com.openclassrooms.safetyNet.model.PersonInfo;
import com.openclassrooms.safetyNet.model.PersonListingForAddress;
import com.openclassrooms.safetyNet.model.PersonListingForFireStation;

import java.util.List;

public interface IBusinessService {
    PersonListingForFireStation getPersonListingForFireStation(int stationNumber);

    List<Child> getChildren(String address);

    List<String> getPhoneNumbers(int firestation);

    PersonListingForAddress getPersonListingForAddress(String address);

    List<String> getEmails(String city);

    PersonInfo getPersonInfo(String firstName, String lastName);
}
