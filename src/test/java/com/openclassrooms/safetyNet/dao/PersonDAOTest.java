package com.openclassrooms.safetyNet.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetyNet.DataSource;
import com.openclassrooms.safetyNet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonDAOTest {

    DataSource dataSource;
    PersonDAO personDAO = new PersonDAO();

    @BeforeEach
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        dataSource =  mapper.readValue(Paths.get("./dataTest.json").toFile(), DataSource.class);;
    }

    @Test
    void getPersonsTest() {
        List<Person> personList = personDAO.getPersons();
        assertEquals(personList.size(),3);
        assertEquals(personList.get(0).getFirstName(),"");
    }

    @Test
    void saveNewPersonTest() {
    }

    @Test
    void findByNameTest() {
    }

    @Test
    void deletePersonTest() {
    }
}