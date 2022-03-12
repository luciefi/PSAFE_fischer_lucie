package com.openclassrooms.safetyNet.IntegrationTest;

import com.openclassrooms.safetyNet.DataSource;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonIntegrationTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    DataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource.initDataSourceBeforeTest();
    }

    @Test
    void getPersonsTest() throws Exception {
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$[0].firstName", is("John"))));
    }

    @Test
    void addPersonTest() throws Exception {
        mockMvc.perform(post("/persons").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"John3\",\n" +
                                "    \"lastName\": \"Boyd3\",\n" +
                                "    \"address\": \"1509 Culver St\",\n" +
                                "    \"city\": \"Culver\",\n" +
                                "    \"zip\": \"97451\",\n" +
                                "    \"phone\": \"841-874-6512\",\n" +
                                "    \"email\": \"jaboyd@email.com\"\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect((jsonPath("$.firstName", is("John3"))));

        Assertions.assertEquals(1, (int) dataSource.getPersons().stream()
                .filter(person -> person.getFirstName().equals("John3") && person.getLastName().equals("Boyd3")).count());
    }

    @Test
    void updatePersonTest() throws Exception {
        mockMvc.perform(put("/persons").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"firstName\": \"Jacob\",\n" +
                                "    \"lastName\": \"Boyd\",\n" +
                                "    \"address\": \"1509 Culver St updated\",\n" +
                                "    \"city\": \"Culver updated\",\n" +
                                "    \"zip\": \"97000\",\n" +
                                "    \"phone\": \"841-874-6512\",\n" +
                                "    \"email\": \"jaboydupdated@email.com\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.city", is("Culver updated"))));
        Person jacobUpdated = dataSource.getPersons().stream()
                .filter(person -> person.getFirstName().equals("Jacob") && person.getLastName().equals("Boyd"))
                .collect(Collectors.toList()).get(0);
        Assertions.assertEquals("1509 Culver St updated", jacobUpdated.getAddress());
        Assertions.assertEquals("Culver updated", jacobUpdated.getCity());
        Assertions.assertEquals(97000, jacobUpdated.getZip());
        Assertions.assertEquals("841-874-6512", jacobUpdated.getPhone());
        Assertions.assertEquals("jaboydupdated@email.com", jacobUpdated.getEmail());
    }

    @Test
    void deletePersonTest() throws Exception {
        mockMvc.perform(delete("/persons/John_Boyd")).andExpect(status().isNoContent());
        List<Person> john = dataSource.getPersons().stream()
                .filter(person -> person.getFirstName().equals("John") && person.getLastName().equals("Boyd"))
                .collect(Collectors.toList());
        assertThat(john).isEmpty();
    }
}