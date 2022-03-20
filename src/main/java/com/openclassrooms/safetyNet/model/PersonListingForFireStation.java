package com.openclassrooms.safetyNet.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PersonListingForFireStation {
    int numberOfAdult;
    int numberOfChildren;
    List<LightweightPerson> personsListForFireStation = new ArrayList<>();
}
