package com.openclassrooms.safetyNet.IntegrationTest.controller;

import com.openclassrooms.safetyNet.model.Child;
import com.openclassrooms.safetyNet.model.FireStation;
import com.openclassrooms.safetyNet.model.PersonListingForFireStation;
import com.openclassrooms.safetyNet.service.BusinessService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

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
    void getPersonsForGivenStation() throws Exception {
        when(businessService.getPersonListingForFireStation(anyInt())).thenReturn(new PersonListingForFireStation());
        mockMvc.perform(get("/firestation").param("stationNumber", "1"))
                .andExpect(status().isOk());
        verify(businessService, Mockito.times(1)).getPersonListingForFireStation(anyInt());

    }

    @Test
    void getChildrenForGivenAddress() throws Exception {
        when(businessService.getChildren(anyString())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/childAlert").param("address","my address"))
                .andExpect(status().isOk());
        verify(businessService, Mockito.times(1)).getChildren(anyString());

    }

    @Test
    void getPhoneNumbersForStation() throws Exception {
        when(businessService.getPhoneNumbers(anyInt())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/phoneAlert").param("firestation", "1"))
                .andExpect(status().isOk());
        verify(businessService, Mockito.times(1)).getPhoneNumbers(anyInt());
    }

}
