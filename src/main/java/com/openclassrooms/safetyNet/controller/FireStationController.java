package com.openclassrooms.safetyNet.controller;

import com.openclassrooms.safetyNet.model.FireStation;
import com.openclassrooms.safetyNet.model.Person;
import com.openclassrooms.safetyNet.service.IFireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class FireStationController {

    private IFireStationService fireStationService;

    @Autowired
    public FireStationController(IFireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping("/firestations")
    public ResponseEntity<?> getFireStation() {
        List<FireStation> fireStations = fireStationService.getAll();
        if (fireStations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(fireStations, HttpStatus.OK);
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
