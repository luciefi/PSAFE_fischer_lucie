package com.openclassrooms.safetyNet.controller;

import com.openclassrooms.safetyNet.model.FireStation;
import com.openclassrooms.safetyNet.service.IFireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class FireStationController {

    private IFireStationService fireStationService;

    @Autowired
    public FireStationController(IFireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @PostMapping("/firestations")
    public ResponseEntity<?> addAddressStationMapping(@Valid @RequestBody FireStation newFireStation) {
        fireStationService.save(newFireStation);
        return ResponseEntity.created(URI.create("firestations")).body(newFireStation);
    }

    @PutMapping("/firestations")
    public ResponseEntity<?> updatePerson(@Valid @RequestBody FireStation newFireStation) {
        FireStation updatedFireStation = fireStationService.update(newFireStation);
        return ResponseEntity.ok(updatedFireStation);
    }

    @DeleteMapping("/firestations/{mapping}")
    public ResponseEntity<?> deletePerson(@PathVariable("mapping") final String mapping) {
        fireStationService.delete(mapping);
        return ResponseEntity.noContent().build();
    }
}
