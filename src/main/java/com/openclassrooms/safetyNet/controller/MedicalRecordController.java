package com.openclassrooms.safetyNet.controller;

import com.openclassrooms.safetyNet.model.MedicalRecord;
import com.openclassrooms.safetyNet.service.IMedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class MedicalRecordController {

    private final IMedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(IMedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/medicalRecord")
    public ResponseEntity<?> getMedicalRecords() {
        List<MedicalRecord> medicalRecords = medicalRecordService.getAll();
        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<?> addMedicalRecord( @RequestBody MedicalRecord newMedicalRecord) {
        medicalRecordService.save(newMedicalRecord);
        return ResponseEntity.created(URI.create("persons")).body(newMedicalRecord);
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<?> updateMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {
        MedicalRecord updatedMedicalRecord = medicalRecordService.update(medicalRecord);
        return ResponseEntity.ok(updatedMedicalRecord);
    }

    @DeleteMapping("/medicalRecord/{fullName}")
    public ResponseEntity<?> deleteMedicalRecord(@PathVariable("fullName") final String fullName) {
        medicalRecordService.delete(fullName);
        return ResponseEntity.ok().build();
    }
}
