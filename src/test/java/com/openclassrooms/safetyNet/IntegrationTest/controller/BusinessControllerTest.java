package com.openclassrooms.safetyNet.IntegrationTest.controller;

import com.openclassrooms.safetyNet.dto.PersonInfoDTO;
import com.openclassrooms.safetyNet.dto.PersonListingForAddressDTO;
import com.openclassrooms.safetyNet.dto.PersonListingForFireStationDTO;
import com.openclassrooms.safetyNet.exceptions.FireStationNotFoundException;
import com.openclassrooms.safetyNet.exceptions.MedicalRecordNotFoundException;
import com.openclassrooms.safetyNet.model.MedicalRecord;
import com.openclassrooms.safetyNet.service.BusinessService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BusinessControllerTest {

    @MockBean
    BusinessService businessService;

    @Autowired
    public MockMvc mockMvc;

    @Test
    void getPersonsForGivenStationTest() throws Exception {
        when(businessService.getPersonListingForFireStation(anyInt())).thenReturn(new PersonListingForFireStationDTO());
        mockMvc.perform(get("/firestation").param("stationNumber", "1"))
                .andExpect(status().isOk());
        verify(businessService, Mockito.times(1)).getPersonListingForFireStation(anyInt());
    }

    @Test
    void getChildrenForGivenAddressTest() throws Exception {
        when(businessService.getChildren(anyString())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/childAlert").param("address","my address"))
                .andExpect(status().isOk());
        verify(businessService, Mockito.times(1)).getChildren(anyString());
    }

    @Test
    void getUnknownChildrenForGivenAddressTest() throws Exception {
        when(businessService.getChildren(anyString())).thenThrow(new MedicalRecordNotFoundException());
        mockMvc.perform(get("/childAlert").param("address","my address"))
                .andExpect(status().isNotFound());
        verify(businessService, Mockito.times(1)).getChildren(anyString());
    }

    @Test
    void getPhoneNumbersForStationTest() throws Exception {
        when(businessService.getPhoneNumbers(anyInt())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/phoneAlert").param("firestation", "1"))
                .andExpect(status().isOk());
        verify(businessService, Mockito.times(1)).getPhoneNumbers(anyInt());
    }

    @Test
    void getPeopleAndStationForAddressTest() throws Exception {
        when(businessService.getPersonListingForAddress(anyString())).thenReturn(new PersonListingForAddressDTO());
        mockMvc.perform(get("/fire").param("address", "my address"))
                .andExpect(status().isOk());
        verify(businessService, Mockito.times(1)).getPersonListingForAddress(anyString());
    }

    @Test
    void getUnknownPeopleAndStationForAddressTest() throws Exception {
        when(businessService.getPersonListingForAddress(anyString())).thenThrow(new MedicalRecordNotFoundException());
        mockMvc.perform(get("/fire").param("address", "my address"))
                .andExpect(status().isNotFound());
        verify(businessService, Mockito.times(1)).getPersonListingForAddress(anyString());
    }

    @Test
    void getPeopleAndUnknownStationForAddressTest() throws Exception {
        when(businessService.getPersonListingForAddress(anyString())).thenThrow(new FireStationNotFoundException());
        mockMvc.perform(get("/fire").param("address", "my address"))
                .andExpect(status().isNotFound());
        verify(businessService, Mockito.times(1)).getPersonListingForAddress(anyString());
    }

    @Test
    void getEmailAddressForCityTest() throws Exception {
        when(businessService.getEmails(anyString())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/communityEmail").param("city", "my city"))
                .andExpect(status().isOk());
        verify(businessService, Mockito.times(1)).getEmails(anyString());
    }

    @Test
    void getPersonInfoTest() throws Exception {
        when(businessService.getPersonInfo(anyString(), anyString())).thenReturn(new PersonInfoDTO());
        mockMvc.perform(get("/personInfo").param("firstName", "f").param("lastName", "l"))
                .andExpect(status().isOk());
        verify(businessService, Mockito.times(1)).getPersonInfo(anyString(), anyString());
    }


    @Test
    void getUnknownPersonInfoTest() throws Exception {
        when(businessService.getPersonInfo(anyString(), anyString())).thenThrow(new MedicalRecordNotFoundException());
        mockMvc.perform(get("/personInfo").param("firstName", "f").param("lastName", "l"))
                .andExpect(status().isNotFound());
        verify(businessService, Mockito.times(1)).getPersonInfo(anyString(), anyString());
    }

    @Test
    void getPersonsForListOfStations() throws Exception {
        when(businessService.getPersonsForListOfStations(any(List.class))).thenReturn(new ArrayList());
        mockMvc.perform(get("/flood/stations").param("stations", "1,2"))
                .andExpect(status().isOk());
        verify(businessService, Mockito.times(1)).getPersonsForListOfStations(any(List.class));
    }

    @Test
    void getUnknownPersonsForListOfStations() throws Exception {
        when(businessService.getPersonsForListOfStations(any(List.class))).thenThrow(new MedicalRecordNotFoundException());
        mockMvc.perform(get("/flood/stations").param("stations", "1,2"))
                .andExpect(status().isNotFound());
        verify(businessService, Mockito.times(1)).getPersonsForListOfStations(any(List.class));
    }
}
