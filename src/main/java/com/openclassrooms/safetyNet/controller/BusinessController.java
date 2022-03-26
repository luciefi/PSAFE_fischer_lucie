package com.openclassrooms.safetyNet.controller;

import com.openclassrooms.safetyNet.model.Child;
import com.openclassrooms.safetyNet.model.PersonListingForAddress;
import com.openclassrooms.safetyNet.model.PersonListingForFireStation;
import com.openclassrooms.safetyNet.service.IBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BusinessController {

    private final IBusinessService businessService;

    @Autowired
    public BusinessController(IBusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/firestation")
    public ResponseEntity<?> getPersonsForGivenStation(
            @RequestParam(value = "stationNumber") int stationNumber
    ) {
        PersonListingForFireStation personListingForFireStation = businessService.getPersonListingForFireStation(stationNumber);
        return new ResponseEntity<>(personListingForFireStation, HttpStatus.OK);
    }

    @GetMapping("/childAlert")
    public ResponseEntity<?> getChildrenForGivenAddress(
            @RequestParam(value = "address") String address
    ) {
        List<Child> children = businessService.getChildren(address);
        return new ResponseEntity<>(children, HttpStatus.OK);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<?> getPhoneNumbersForStation(
            @RequestParam(value = "firestation") int firestation
    ) {
        List<String> phoneNumbers = businessService.getPhoneNumbers(firestation);
        return new ResponseEntity<>(phoneNumbers, HttpStatus.OK);
    }

    @GetMapping("/fire")
    public ResponseEntity<?> getPeopleAndStationForAddress(@RequestParam(value = "address")String address){
        PersonListingForAddress personListingForAddress = businessService.getPersonListingForAddress(address);
        return new ResponseEntity<>(personListingForAddress, HttpStatus.OK);
    }
}

