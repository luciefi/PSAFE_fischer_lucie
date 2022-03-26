package com.openclassrooms.safetyNet.IntegrationTest.controller;

import com.openclassrooms.safetyNet.exceptions.FireStationAlreadyExistsException;
import com.openclassrooms.safetyNet.exceptions.InvalidFormattedFullNameException;
import com.openclassrooms.safetyNet.exceptions.PersonNotFoundException;
import com.openclassrooms.safetyNet.model.FireStation;
import com.openclassrooms.safetyNet.service.FireStationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FireStationControllerTest {

    @MockBean
    FireStationService fireStationService;

    @Autowired
    public MockMvc mockMvc;

    @Test
    void getFireStationsTest() throws Exception {
        List<FireStation> fireStations = new ArrayList<>();
        FireStation stationTest = new FireStation();
        stationTest.setAddress("Culver St");
        stationTest.setStation(1);
        fireStations.add(stationTest);
        when(fireStationService.getAll()).thenReturn(fireStations);
        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk());
        verify(fireStationService, Mockito.times(1)).getAll();
    }

    @Test
    void addFireStationTest() throws Exception {
        when(fireStationService.save(any(FireStation.class))).thenReturn(new FireStation());

        mockMvc.perform(post("/firestations").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"address\": \"1 Culver St\",\n" +
                                "    \"station\": \"1\"\n" +
                                "}"))
                .andExpect(status().isCreated());
        verify(fireStationService, Mockito.times(1)).save(any(FireStation.class));
    }

    @Test
    void addAlreadyKnownFireStationTest() throws Exception {
        when(fireStationService.save(any(FireStation.class))).thenThrow(new FireStationAlreadyExistsException());

        mockMvc.perform(post("/firestations").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"address\": \"1509 Culver St\",\n" +
                                "    \"station\": \"1\"\n" +
                                "}"))
                .andExpect(status().isConflict());
        verify(fireStationService, Mockito.times(1)).save(any(FireStation.class));
    }

    @Test
    void addInvalidFireStationTest() throws Exception {
        mockMvc.perform(post("/firestations").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"address\": \"1 Culver St\",\n" +
                                "    \"station\": \"\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateFireStationTest() throws Exception {
        when(fireStationService.update(any(FireStation.class))).thenReturn(new FireStation());
        mockMvc.perform(put("/firestations").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"address\": \"1509 Culver St updated\",\n" +
                                "    \"station\": \"1\"\n" +
                                "}"))
                .andExpect(status().isOk());
        verify(fireStationService, Mockito.times(1)).update(any(FireStation.class));
    }

    @Test
    void updateUnknownFireStationTest() throws Exception {
        when(fireStationService.update(any(FireStation.class))).thenThrow(new FireStationAlreadyExistsException());
        mockMvc.perform(put("/firestations").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"address\": \"1 Culver St\",\n" +
                                "    \"station\": \"1\"\n" +
                                "}"))
                .andExpect(status().isConflict());
        verify(fireStationService, Mockito.times(1)).update(any(FireStation.class));
    }


    @Test
    void updateInvalidFireStationTest() throws Exception {
        mockMvc.perform(put("/firestations").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"address\": \"1 Culver St\",\n" +
                                "    \"station\": \"\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteFireStationTest() throws Exception {
        mockMvc.perform(delete("/firestations/my_address")).andExpect(status().isNoContent());
        verify(fireStationService, Mockito.times(1)).delete(anyString());
    }

    @Test
    void deleteUnknownFireStationTest() throws Exception {
        Mockito.doThrow(new PersonNotFoundException()).when(fireStationService).delete(anyString());
        mockMvc.perform(delete("/firestations/my_address")).andExpect(status().isNotFound());
        verify(fireStationService, Mockito.times(1)).delete(anyString());
    }

    @Test
    void deleteInvalidFireStationTest() throws Exception {
        Mockito.doThrow(new InvalidFormattedFullNameException()).when(fireStationService).delete(anyString());
        mockMvc.perform(delete("/firestations/my_address")).andExpect(status().isBadRequest());
        verify(fireStationService, Mockito.times(1)).delete(anyString());
    }

    @Test
    void deleteInternalErrorTest() throws Exception {
        Mockito.doThrow(new InternalError()).when(fireStationService).delete(anyString());
        mockMvc.perform(delete("/firestations/my_address")).andExpect(status().isInternalServerError());
        verify(fireStationService, Mockito.times(1)).delete(anyString());
    }
}

