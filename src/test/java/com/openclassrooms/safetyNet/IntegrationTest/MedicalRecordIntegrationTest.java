package com.openclassrooms.safetyNet.IntegrationTest;

import com.openclassrooms.safetyNet.DataSource;
import com.openclassrooms.safetyNet.model.MedicalRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MedicalRecordIntegrationTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    DataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource.initDataSourceBeforeTest();
    }

    @Test
    void getMedicalRecordsTest() throws Exception {
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$[0].firstName", is("John"))));
    }

    @Test
    void addMedicalRecordTest() throws Exception {
        mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"John3\",\n" +
                                "    \"lastName\": \"Boyd3\",\n" +
                                "    \"birthdate\":\"03/06/1984\",\n" +
                                "    \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \n" +
                                "    \"allergies\":[\"nillacilan\"]" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect((jsonPath("$.firstName", is("John3"))));

        Assertions.assertEquals(1, (int) dataSource.getMedicalrecords().stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals("John3") && medicalRecord.getLastName().equals("Boyd3")).count());
    }

    @Test
    void updateMedicalRecordTest() throws Exception {
        mockMvc.perform(put("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"Jacob\",\n" +
                                "    \"lastName\": \"Boyd\",\n" +
                                "    \"birthdate\":\"03/06/2000\",\n" +
                                "    \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \n" +
                                "    \"allergies\":[\"nillacilan\"]" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.birthdate", is("03/06/2000"))));
        MedicalRecord jacobUpdated = dataSource.getMedicalrecords().stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals("Jacob") && medicalRecord.getLastName().equals("Boyd"))
                .collect(Collectors.toList()).get(0);
        Assertions.assertEquals("Mon Mar 06 01:00:00 CET 2000", jacobUpdated.getBirthdate().toString());
        Assertions.assertEquals("[aznol:350mg, hydrapermazol:100mg]", jacobUpdated.getMedications().toString());
        Assertions.assertEquals("[nillacilan]", jacobUpdated.getAllergies().toString());
    }

    @Test
    void deleteMedicalRecordTest() throws Exception {
        mockMvc.perform(delete("/medicalRecord/John_Boyd")).andExpect(status().isNoContent());
        List<MedicalRecord> john = dataSource.getMedicalrecords().stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals("John") && medicalRecord.getLastName().equals("Boyd"))
                .collect(Collectors.toList());
        assertThat(john).isEmpty();
    }
}