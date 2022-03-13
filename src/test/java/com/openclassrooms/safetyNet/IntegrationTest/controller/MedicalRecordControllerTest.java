package com.openclassrooms.safetyNet.IntegrationTest.controller;

import com.openclassrooms.safetyNet.exceptions.InvalidFormattedFullNameException;
import com.openclassrooms.safetyNet.exceptions.MedicalRecordAlreadyExistsException;
import com.openclassrooms.safetyNet.exceptions.PersonAlreadyExistsException;
import com.openclassrooms.safetyNet.exceptions.PersonNotFoundException;
import com.openclassrooms.safetyNet.model.MedicalRecord;
import com.openclassrooms.safetyNet.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MedicalRecordControllerTest {

    @MockBean
    MedicalRecordService medicalRecordService;

    @Autowired
    public MockMvc mockMvc;

    @Test
    void getMedicalRecordsTest() throws Exception {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        MedicalRecord totoTest = new MedicalRecord();
        totoTest.setBirthdate(new Date());
        totoTest.setMedications(new ArrayList<>());
        totoTest.setAllergies(new ArrayList<>());
        medicalRecords.add(totoTest);
        when(medicalRecordService.getAll()).thenReturn(medicalRecords);
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk());
        verify(medicalRecordService, Mockito.times(1)).getAll();
    }

    @Test
    void getEmptyMedicalRecordTest() throws Exception {
        when(medicalRecordService.getAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isNoContent());
        verify(medicalRecordService, Mockito.times(1)).getAll();
    }

    @Test
    void addMedicalRecordTest() throws Exception {
        when(medicalRecordService.save(any(MedicalRecord.class))).thenReturn(new MedicalRecord());

        mockMvc.perform(post("/medicalRecord/").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"John3\",\n" +
                                "    \"lastName\": \"Boyd3\",\n" +
                                "    \"birthdate\":\"03/06/1984\",\n" +
                                "    \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \n" +
                                "    \"allergies\":[\"nillacilan\"]" +
                                "}"))
                .andExpect(status().isCreated());
        verify(medicalRecordService, Mockito.times(1)).save(any(MedicalRecord.class));
    }

    @Test
    void addAlreadyKnownMedicalRecordTest() throws Exception {
        when(medicalRecordService.save(any(MedicalRecord.class))).thenThrow(new MedicalRecordAlreadyExistsException());

        mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"John\",\n" +
                                "    \"lastName\": \"Boyd\",\n" +
                                "    \"birthdate\":\"03/06/1984\",\n" +
                                "    \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \n" +
                                "    \"allergies\":[\"nillacilan\"]" +
                                "}"))
                .andExpect(status().isConflict());
        verify(medicalRecordService, Mockito.times(1)).save(any(MedicalRecord.class));
    }

    @Test
    void addInvalidMedicalRecordTest() throws Exception {
        mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"John\",\n" +
                                "    \"lastName\": \"Boyd\",\n" +
                                "    \"birthdate\":\"\",\n" +
                                "    \"medications\":\"\", \n" +
                                "    \"allergies\":\"\"" +
                                "}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void updateMedicalRecordTest() throws Exception {
        when(medicalRecordService.update(any(MedicalRecord.class))).thenReturn(new MedicalRecord());
        mockMvc.perform(put("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"John\",\n" +
                                "    \"lastName\": \"Boyd\",\n" +
                                "    \"birthdate\":\"03/06/1984\",\n" +
                                "    \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \n" +
                                "    \"allergies\":[\"nillacilan\"]" +
                                "}"))
                .andExpect(status().isOk());
        verify(medicalRecordService, Mockito.times(1)).update(any(MedicalRecord.class));
    }

    @Test
    void updateUnknownMedicalRecordTest() throws Exception {
        when(medicalRecordService.update(any(MedicalRecord.class))).thenThrow(new PersonAlreadyExistsException());
        mockMvc.perform(put("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"Jane\",\n" +
                                "    \"lastName\": \"Boyd\",\n" +
                                "    \"birthdate\":\"03/06/1984\",\n" +
                                "    \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \n" +
                                "    \"allergies\":[\"nillacilan\"]" +
                                "}"))
                .andExpect(status().isConflict());
        verify(medicalRecordService, Mockito.times(1)).update(any(MedicalRecord.class));
    }


    @Test
    void updateInvalidMedicalRecordTest() throws Exception {
        mockMvc.perform(put("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"John\",\n" +
                                "    \"lastName\": \"Boyd\",\n" +
                                "    \"birthdate\":\"\",\n" +
                                "    \"medications\":\"\", \n" +
                                "    \"allergies\":\"\"" +
                                "}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void deleteMedicalRecordTest() throws Exception {
        mockMvc.perform(delete("/medicalRecord/John_Boyd")).andExpect(status().isNoContent());
        verify(medicalRecordService, Mockito.times(1)).delete(anyString());
    }

    @Test
    void deleteUnknownMedicalRecordTest() throws Exception {
        Mockito.doThrow(new PersonNotFoundException()).when(medicalRecordService).delete(anyString());
        mockMvc.perform(delete("/medicalRecord/Jane_Boyd")).andExpect(status().isNotFound());
        verify(medicalRecordService, Mockito.times(1)).delete(anyString());
    }

    @Test
    void deleteInvalidNameTest() throws Exception {
        Mockito.doThrow(new InvalidFormattedFullNameException()).when(medicalRecordService).delete(anyString());
        mockMvc.perform(delete("/medicalRecord/Jane_Boyd")).andExpect(status().isBadRequest());
        verify(medicalRecordService, Mockito.times(1)).delete(anyString());
    }

    @Test
    void deleteInternalErrorTest() throws Exception {
        Mockito.doThrow(new InternalError()).when(medicalRecordService).delete(anyString());
        mockMvc.perform(delete("/medicalRecord/Jane_Boyd")).andExpect(status().isInternalServerError());
        verify(medicalRecordService, Mockito.times(1)).delete(anyString());
    }
}

