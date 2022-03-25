package com.openclassrooms.safetyNet.IntegrationTest.controller;

import com.openclassrooms.safetyNet.exceptions.InvalidFormattedFullNameException;
import com.openclassrooms.safetyNet.exceptions.PersonAlreadyExistsException;
import com.openclassrooms.safetyNet.exceptions.PersonNotFoundException;
import com.openclassrooms.safetyNet.model.Person;
import com.openclassrooms.safetyNet.service.PersonService;
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
class PersonControllerTest {

    @MockBean
    PersonService personService;

    @Autowired
    public MockMvc mockMvc;

    @Test
    void getPersonsTest() throws Exception {
        List<Person> persons = new ArrayList<>();
        Person totoTest = new Person();
        totoTest.setFirstName("jojo");
        totoTest.setLastName("test");
        persons.add(totoTest);
        when(personService.getAll()).thenReturn(persons);
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk());
        verify(personService, Mockito.times(1)).getAll();
    }

    @Test
    void addPersonTest() throws Exception {
        when(personService.save(any(Person.class))).thenReturn(new Person());

        mockMvc.perform(post("/persons/").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"Jane\",\n" +
                                "    \"lastName\": \"Boyd3\",\n" +
                                "    \"address\": \"1509 Culver St\",\n" +
                                "    \"city\": \"Culver\",\n" +
                                "    \"zip\": \"97451\",\n" +
                                "    \"phone\": \"841-874-6512\",\n" +
                                "    \"email\": \"jaboyd@email.com\"\n" +
                                "}"))
                .andExpect(status().isCreated());
        verify(personService, Mockito.times(1)).save(any(Person.class));
    }

    @Test
    void addAlreadyKnownPersonTest() throws Exception {
        when(personService.save(any(Person.class))).thenThrow(new PersonAlreadyExistsException());

        mockMvc.perform(post("/persons").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"John\",\n" +
                                "    \"lastName\": \"Boyd\",\n" +
                                "    \"address\": \"1509 Culver St\",\n" +
                                "    \"city\": \"Culver\",\n" +
                                "    \"zip\": \"97451\",\n" +
                                "    \"phone\": \"841-874-6512\",\n" +
                                "    \"email\": \"jaboyd@email.com\"\n" +
                                "}"))
                .andExpect(status().isConflict());
        verify(personService, Mockito.times(1)).save(any(Person.class));
    }

    @Test
    void addInvalidPersonTest() throws Exception {
        mockMvc.perform(post("/persons").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"John\",\n" +
                                "    \"lastName\": \"Boyd\",\n" +
                                "    \"address\": \"\",\n" +
                                "    \"city\": \"\",\n" +
                                "    \"zip\": \"\",\n" +
                                "    \"phone\": \"\",\n" +
                                "    \"email\": \"\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updatePersonTest() throws Exception {
        when(personService.update(any(Person.class))).thenReturn(new Person());
        mockMvc.perform(put("/persons").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"John\",\n" +
                                "    \"lastName\": \"Boyd\",\n" +
                                "    \"address\": \"1509 Culver St updated\",\n" +
                                "    \"city\": \"Culver updated\",\n" +
                                "    \"zip\": \"97000\",\n" +
                                "    \"phone\": \"841-874-6512\",\n" +
                                "    \"email\": \"jaboydupdated@email.com\"\n" +
                                "}"))
                .andExpect(status().isOk());
        verify(personService, Mockito.times(1)).update(any(Person.class));
    }

    @Test
    void updateUnknownPersonTest() throws Exception {
        when(personService.update(any(Person.class))).thenThrow(new PersonAlreadyExistsException());
        mockMvc.perform(put("/persons").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"Jane\",\n" +
                                "    \"lastName\": \"Boyd\",\n" +
                                "    \"address\": \"1509 Culver St updated\",\n" +
                                "    \"city\": \"Culver updated\",\n" +
                                "    \"zip\": \"97000\",\n" +
                                "    \"phone\": \"841-874-6512\",\n" +
                                "    \"email\": \"jaboydupdated@email.com\"\n" +
                                "}"))
                .andExpect(status().isConflict());
        verify(personService, Mockito.times(1)).update(any(Person.class));
    }


    @Test
    void updateInvalidPersonTest() throws Exception {
        mockMvc.perform(put("/persons").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"John\",\n" +
                                "    \"lastName\": \"Boyd\",\n" +
                                "    \"address\": \"\",\n" +
                                "    \"city\": \"\",\n" +
                                "    \"zip\": \"\",\n" +
                                "    \"phone\": \"\",\n" +
                                "    \"email\": \"\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletePersonTest() throws Exception {
        mockMvc.perform(delete("/persons/John_Boyd")).andExpect(status().isNoContent());
        verify(personService, Mockito.times(1)).delete(anyString());
    }

    @Test
    void deleteUnknownPersonTest() throws Exception {
        Mockito.doThrow(new PersonNotFoundException()).when(personService).delete(anyString());
        mockMvc.perform(delete("/persons/Jane_Boyd")).andExpect(status().isNotFound());
        verify(personService, Mockito.times(1)).delete(anyString());
    }

    @Test
    void deleteInvalidNameTest() throws Exception {
        Mockito.doThrow(new InvalidFormattedFullNameException()).when(personService).delete(anyString());
        mockMvc.perform(delete("/persons/Jane_Boyd")).andExpect(status().isBadRequest());
        verify(personService, Mockito.times(1)).delete(anyString());
    }

    @Test
    void deleteInternalErrorTest() throws Exception {
        Mockito.doThrow(new InternalError()).when(personService).delete(anyString());
        mockMvc.perform(delete("/persons/Jane_Boyd")).andExpect(status().isInternalServerError());
        verify(personService, Mockito.times(1)).delete(anyString());
    }
}

