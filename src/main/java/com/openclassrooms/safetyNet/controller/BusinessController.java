package com.openclassrooms.safetyNet.controller;

import com.openclassrooms.safetyNet.model.*;
import com.openclassrooms.safetyNet.service.IBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class BusinessController {

    private final IBusinessService businessService;

    @Autowired
    public BusinessController(IBusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/firestation")
    public ResponseEntity<?> getPersonsForGivenStation(@RequestParam int stationNumber) {
        PersonListingForFireStation personListingForFireStation = businessService.getPersonListingForFireStation(stationNumber);
        return new ResponseEntity<>(personListingForFireStation, HttpStatus.OK);
    }

    @GetMapping("/childAlert")
    public ResponseEntity<?> getChildrenForGivenAddress(@RequestParam String address) {
        List<Child> children = businessService.getChildren(address);
        return new ResponseEntity<>(children, HttpStatus.OK);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<?> getPhoneNumbersForStation(@RequestParam int firestation) {
        List<String> phoneNumbers = businessService.getPhoneNumbers(firestation);
        return new ResponseEntity<>(phoneNumbers, HttpStatus.OK);
    }

    @GetMapping("/fire")
    public ResponseEntity<?> getPeopleAndStationForAddress(@RequestParam String address) {
        PersonListingForAddress personListingForAddress = businessService.getPersonListingForAddress(address);
        return new ResponseEntity<>(personListingForAddress, HttpStatus.OK);
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<?> getEmailAddressForCity(@RequestParam String city) {
        List<String> emails = businessService.getEmails(city);
        return new ResponseEntity<>(emails, HttpStatus.OK);
    }

    @GetMapping("/personInfo")
    public ResponseEntity<?> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) {
        PersonInfo personInfo = businessService.getPersonInfo(firstName, lastName);
        return new ResponseEntity<>(personInfo, HttpStatus.OK);
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<?> getPersonsForListOfStations(@RequestParam List<Integer> stations) {
        List<StationWithAddressAndPersonList> stationWithAddressAndPersonList =
                businessService.getPersonsForListOfStations(stations);
        return new ResponseEntity<>(stationWithAddressAndPersonList, HttpStatus.OK);
    }
}