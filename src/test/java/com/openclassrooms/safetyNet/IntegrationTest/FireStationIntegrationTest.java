package com.openclassrooms.safetyNet.IntegrationTest;

import com.openclassrooms.safetyNet.DataSource;
import com.openclassrooms.safetyNet.model.FireStation;
import com.openclassrooms.safetyNet.model.Person;
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
class FireStationIntegrationTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    DataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource.initDataSourceBeforeTest();
    }

    @Test
    void getFireStationMappingTest() throws Exception {
        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$[0].address", is("1509 Culver St"))));
    }

    @Test
    void addFireStationMappingTest() throws Exception {
        mockMvc.perform(post("/firestations").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"address\": \"1 Culver St\",\n" +
                                "    \"station\": \"1\"\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect((jsonPath("$.address", is("1 Culver St"))));

        Assertions.assertEquals(1, (int) dataSource.getFirestations().stream()
                .filter(fireStation -> fireStation.getAddress().equals("1 Culver St")).count());
    }

    @Test
    void updateFireStationMappingTest() throws Exception {
        mockMvc.perform(put("/firestations").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"address\": \"1509 Culver St\",\n" +
                                "    \"station\": \"17\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.station", is(17))));
        FireStation culverUpdated = dataSource.getFirestations().stream()
                .filter(fireStation -> fireStation.getAddress().equals("1509 Culver St"))
                .collect(Collectors.toList()).get(0);
        Assertions.assertEquals("1509 Culver St", culverUpdated.getAddress());
        Assertions.assertEquals(17, culverUpdated.getStation());
    }

    @Test
    void deleteFireStationMappingTest() throws Exception {
        mockMvc.perform(delete("/firestations/1509 Culver St")).andExpect(status().isNoContent());
        List<FireStation> culver = dataSource.getFirestations().stream()
                .filter(fireStation -> fireStation.getAddress().equals("1509 Culver St"))
                .collect(Collectors.toList());
        assertThat(culver).isEmpty();
    }
}