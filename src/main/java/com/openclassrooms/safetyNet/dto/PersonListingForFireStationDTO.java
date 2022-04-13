package com.openclassrooms.safetyNet.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PersonListingForFireStationDTO {
    int numberOfAdult;
    int numberOfChildren;
    List<LightweightPersonDTO> personsListForFireStation = new ArrayList<>();
}
