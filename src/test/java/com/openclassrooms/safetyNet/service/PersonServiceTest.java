package com.openclassrooms.safetyNet.service;

import com.openclassrooms.safetyNet.dao.IPersonDAO;
import com.openclassrooms.safetyNet.exceptions.InvalidFormattedFullNameException;
import com.openclassrooms.safetyNet.exceptions.PersonAlreadyExistsException;
import com.openclassrooms.safetyNet.exceptions.PersonNotFoundException;
import com.openclassrooms.safetyNet.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private IPersonDAO personDAO;

    @InjectMocks
    private PersonService personService;

    @Test
    public void getAllPersonsTest() {
        List persons = new ArrayList();
        persons.add(new Person());
        when(personDAO.getPersons()).thenReturn(persons);

        assertEquals(1, personService.getAll().size());
        verify(personDAO, Mockito.times(1)).getPersons();
    }

    @Test
    public void saveAlreadyExistingPersonTest() {
        when(personDAO.findByName(any(String[].class))).thenReturn(new Person());
        assertThrows(PersonAlreadyExistsException.class, () -> {
            personService.save(new Person());
        });
        verify(personDAO, Mockito.times(0)).saveNewPerson(any(Person.class));
    }

    @Test
    public void saveNewPersonTest() {
        when(personDAO.saveNewPerson(any(Person.class))).thenReturn(new Person());
        when(personDAO.findByName(any(String[].class))).thenReturn(null);
        personService.save(new Person());
        verify(personDAO, Mockito.times(1)).saveNewPerson(any(Person.class));
    }

    @Test
    public void findKnownPersonByNameTest() {
        when(personDAO.findByName(any(String[].class))).thenReturn(new Person());
        assertEquals(new Person(), personService.findByName("toto_test"));
        verify(personDAO, Mockito.times(1)).findByName(any(String[].class));
    }

    @Test
    public void findUnknownPersonByNameTest() {
        when(personDAO.findByName(any(String[].class))).thenReturn(null);
        assertEquals(null, personService.findByName("toto_test"));
        verify(personDAO, Mockito.times(1)).findByName(any(String[].class));
    }

    @Test
    void findInvalidNameTest() {
        assertThrows(InvalidFormattedFullNameException.class, () -> {
            personService.findByName("_");
        });
        verify(personDAO, Mockito.times(0)).findByName(any(String[].class));
    }

    @Test
    public void updateKnownPersonTest() {
        when(personDAO.findByName(any(String[].class))).thenReturn(new Person());
        assertEquals(new Person(), personService.update(new Person()));
        verify(personDAO, Mockito.times(1)).findByName(any(String[].class));
    }

    @Test
    public void updateUnknownPersonTest() {
        when(personDAO.findByName(any(String[].class))).thenReturn(null);
        assertThrows(PersonNotFoundException.class, () -> {
            personService.update(new Person());
        });
        verify(personDAO, Mockito.times(1)).findByName(any(String[].class));
    }

    @Test
    public void deleteKnownPersonTest() {
        when(personDAO.findByName(any(String[].class))).thenReturn(new Person());
        personService.delete("toto_test");
        verify(personDAO, Mockito.times(1)).findByName(any(String[].class));
        verify(personDAO, Mockito.times(1)).deletePerson(any(String[].class));
    }

    @Test
    public void deleteUnknownPersonTest() {
        when(personDAO.findByName(any(String[].class))).thenReturn(null);
        assertThrows(PersonNotFoundException.class, () -> {
            personService.delete("toto_test");
        });
        verify(personDAO, Mockito.times(1)).findByName(any(String[].class));
        verify(personDAO, Mockito.times(0)).deletePerson(any(String[].class));
    }
}