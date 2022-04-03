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

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
        when(personDAO.findAll()).thenReturn(Collections.singletonList(new Person()));
        assertEquals(1, personService.getAll().size());
        verify(personDAO, Mockito.times(1)).findAll();
    }

    @Test
    public void saveAlreadyExistingPersonTest() {
        when(personDAO.findById(anyString(), anyString())).thenReturn(new Person());
        assertThrows(PersonAlreadyExistsException.class, () -> personService.save(new Person()));
        verify(personDAO, Mockito.times(0)).save(any(Person.class));
        verify(personDAO, Mockito.times(1)).findById(anyString(), anyString());
    }

    @Test
    public void saveNewPersonTest() {
        when(personDAO.save(any(Person.class))).thenReturn(new Person());
        when(personDAO.findById(anyString(), anyString())).thenReturn(null);
        personService.save(new Person());
        verify(personDAO, Mockito.times(1)).findById(anyString(), anyString());
        verify(personDAO, Mockito.times(1)).save(any(Person.class));
    }

    @Test
    public void findKnownPersonByNameTest() {
        when(personDAO.findById(anyString(), anyString())).thenReturn(new Person());
        assertEquals(new Person(), personService.findByName("toto_test"));
        verify(personDAO, Mockito.times(1)).findById(anyString(), anyString());
    }

    @Test
    public void findUnknownPersonByNameTest() {
        when(personDAO.findById(anyString(), anyString())).thenReturn(null);
        assertNull(personService.findByName("toto_test"));
        verify(personDAO, Mockito.times(1)).findById(anyString(), anyString());
    }

    @Test
    void findInvalidNameTest() {
        assertThrows(InvalidFormattedFullNameException.class, () -> personService.findByName("_"));
        verify(personDAO, Mockito.times(0)).findById(anyString(), anyString());
    }

    @Test
    public void updateKnownPersonTest() {
        when(personDAO.findById(anyString(), anyString())).thenReturn(new Person());
        assertEquals(new Person(), personService.update(new Person()));
        verify(personDAO, Mockito.times(1)).findById(anyString(), anyString());
    }

    @Test
    public void updateUnknownPersonTest() {
        when(personDAO.findById(anyString(), anyString())).thenReturn(null);
        assertThrows(PersonNotFoundException.class, () -> personService.update(new Person()));
        verify(personDAO, Mockito.times(1)).findById(anyString(), anyString());
    }

    @Test
    public void deleteKnownPersonTest() {
        when(personDAO.findById(anyString(), anyString())).thenReturn(new Person());
        personService.delete("toto_test");
        verify(personDAO, Mockito.times(1)).findById(anyString(), anyString());
        verify(personDAO, Mockito.times(1)).deleteById(anyString(), anyString());
    }

    @Test
    public void deleteUnknownPersonTest() {
        when(personDAO.findById(anyString(), anyString())).thenReturn(null);
        assertThrows(PersonNotFoundException.class, () -> personService.delete("toto_test"));
        verify(personDAO, Mockito.times(1)).findById(anyString(), anyString());
        verify(personDAO, Mockito.times(0)).deleteById(anyString(), anyString());
    }
}