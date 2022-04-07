package com.openclassrooms.safetyNet.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class StationWithAddressAndPersonList {
    private int station;
    List<AddressWithPersonList> addressWithPersonLists;
}
