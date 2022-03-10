package com.openclassrooms.safetyNet.dao;

import com.openclassrooms.safetyNet.DataSource;
import com.openclassrooms.safetyNet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonDAOTest {

    @Mock
    DataSource dataSource;

    PersonDAO personDAO;

    @BeforeEach
    void setUp() {
        personDAO = new PersonDAO(dataSource);
    }

    @Test
    void getPersonsTest() {
        List persons = new ArrayList();
        persons.add(new Person());
        when(dataSource.getPersons()).thenReturn(persons);

        assertEquals(1, personDAO.findAll().size());
        verify(dataSource, Mockito.times(1)).getPersons();
    }

    @Test
    void saveNewPersonTest() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person());
        when(dataSource.getPersons()).thenReturn(persons);
        personDAO.save(new Person());
        assertEquals(2, persons.size());
        verify(dataSource, Mockito.times(1)).getPersons();
    }

    @Test
    void findKnownPersonByNameTest() {
        List<Person> persons = new ArrayList<>();
        Person totoTest = new Person();
        totoTest.setFirstName("toto");
        totoTest.setLastName("test");
        persons.add(totoTest);
        when(dataSource.getPersons()).thenReturn(persons);
        assertNotNull(personDAO.findById(new String[]{"toto", "test"}));
        verify(dataSource, Mockito.times(1)).getPersons();
    }

    @Test
    void findUnknownPersonByNameTest() {
        when(dataSource.getPersons()).thenReturn(new ArrayList<>());
        assertNull(personDAO.findById(new String[]{"toto", "test"}));
        verify(dataSource, Mockito.times(1)).getPersons();
    }

    @Test
    void findFirstNameUnknownPersonByNameTest() {
        List<Person> persons = new ArrayList<>();
        Person totoTest = new Person();
        totoTest.setFirstName("ttotto");
        totoTest.setLastName("test");
        persons.add(totoTest);
        when(dataSource.getPersons()).thenReturn(persons);
        assertNull(personDAO.findById(new String[]{"toto", "test"}));
        verify(dataSource, Mockito.times(1)).getPersons();
    }

    @Test
    void findLastNameUnknownPersonByNameTest() {
        List<Person> persons = new ArrayList<>();
        Person totoTest = new Person();
        totoTest.setFirstName("toto");
        totoTest.setLastName("testTest");
        persons.add(totoTest);
        when(dataSource.getPersons()).thenReturn(persons);
        assertNull(personDAO.findById(new String[]{"toto", "test"}));
        verify(dataSource, Mockito.times(1)).getPersons();
    }

    @Test
    void deletePersonTest() {
        List<Person> persons = new ArrayList<>();
        Person totoTest = new Person();
        totoTest.setFirstName("toto");
        totoTest.setLastName("test");
        persons.add(totoTest);
        when(dataSource.getPersons()).thenReturn(persons);
        personDAO.deleteById(new String[]{"toto", "test"});
        assertEquals(0, persons.size());
    }
}