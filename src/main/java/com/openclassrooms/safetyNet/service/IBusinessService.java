package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.dto.*;

import java.util.List;

public interface IBusinessService {
    PersonListingForFireStationDTO getPersonListingForFireStation(int stationNumber);

    List<ChildDTO> getChildren(String address);

    List<String> getPhoneNumbers(int firestation);

    PersonListingForAddressDTO getPersonListingForAddress(String address);

    List<String> getEmails(String city);

    PersonInfoDTO getPersonInfo(String firstName, String lastName);

    List<StationWithAddressListingDTO> getPersonsForListOfStations(List<Integer> stations);
}
