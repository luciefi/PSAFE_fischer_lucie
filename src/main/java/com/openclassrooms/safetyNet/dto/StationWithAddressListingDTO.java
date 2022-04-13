package com.openclassrooms.safetyNet.dto;

import lombok.Data;

import java.util.List;

@Data
public class StationWithAddressListingDTO {
    private int station;
    List<AddressWithPersonListingDTO> addressListing;
}