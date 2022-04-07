package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.model.*;

import java.util.HashMap;
import java.util.List;

public interface IBusinessService {
    PersonListingForFireStation getPersonListingForFireStation(int stationNumber);

    List<Child> getChildren(String address);

    List<String> getPhoneNumbers(int firestation);

    PersonListingForAddress getPersonListingForAddress(String address);

    List<String> getEmails(String city);

    PersonInfo getPersonInfo(String firstName, String lastName);

    HashMap<Integer, HashMap<String, List<PersonWithMedicalRecord>>> getPersonsForListOfStations(List<Integer> stations);
}
