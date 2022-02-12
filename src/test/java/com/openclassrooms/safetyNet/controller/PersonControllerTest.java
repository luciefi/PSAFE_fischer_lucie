package com.openclassrooms.safetyNet.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    // TODO mocker les services
    // TODO classer parmi les tests d'integration

    @Autowired
    public MockMvc mockMvc;

    @Test
    void getPersonsTest() throws Exception {
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$[0].firstName", is("John"))));
    }

    @Test
    void addPersonTest() throws Exception {
        mockMvc.perform(post("/persons/JaneDoe").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"John3\",\n" +
                                "    \"lastName\": \"Boyd3\",\n" +
                                "    \"address\": \"1509 Culver St\",\n" +
                                "    \"city\": \"Culver\",\n" +
                                "    \"zip\": \"97451\",\n" +
                                "    \"phone\": \"841-874-6512\",\n" +
                                "    \"email\": \"jaboyd@email.com\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.firstName", is("Jane"))));
    }

    @Test
    void updatePersonTest() throws Exception {
        mockMvc.perform(put("/persons/JohnBoyd").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"lastName\": \"Boyd\",\n" +
                        "    \"address\": \"1509 Culver St updated\",\n" +
                        "    \"city\": \"Culver updated\",\n" +
                        "    \"zip\": \"97451\",\n" +
                        "    \"phone\": \"841-874-6512\",\n" +
                        "    \"email\": \"jaboydupdated@email.com\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.city", is("Culver updated"))));
    }

    @Test
    void deletePersonTest() throws Exception {
        mockMvc.perform(delete("/persons/JohnBoyd")).andExpect(status().isOk());
    }
}