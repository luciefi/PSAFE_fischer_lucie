package com.openclassrooms.safetyNet.utils;

import com.openclassrooms.safetyNet.dto.ChildDTO;
import com.openclassrooms.safetyNet.dto.LightweightPersonDTO;
import com.openclassrooms.safetyNet.dto.PersonInfoDTO;
import com.openclassrooms.safetyNet.dto.PersonWithMedicalRecordDTO;
import com.openclassrooms.safetyNet.model.*;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonConverterTest {
    @Test
    void convertToLightweight() {
        // Arrange
        Person person = new Person("toto", "test", "1 street", "myCity", 123, "051234", "email" +
                "@test.com");

        // Act
        LightweightPersonDTO lightweightPerson = PersonConverter.convertToLightweight(person);

        // Assert
        assertThat(lightweightPerson.getFirstName()).isEqualTo("toto");
        assertThat(lightweightPerson.getLastName()).isEqualTo("test");
        assertThat(lightweightPerson.getAddress()).isEqualTo("1 street");
        assertThat(lightweightPerson.getPhone()).isEqualTo("051234");
    }

    @Test
    void convertToLightweightNull() {
        Assertions.assertNull(PersonConverter.convertToLightweight(null));
    }

    @Test
    void convertToChild() {
        // Arrange
        Person person = new Person("toto", "test", "1 street", "myCity", 123, "051234", "email" +
                "@test.com");
        int age = 10;

        List<Person> householdList = new ArrayList<>();
        Person person2 = new Person("toto2", "test", "1 street", "myCity", 123, "051234", "email" +
                "@test.com");
        householdList.add(person2);
        householdList.add(person2);
        householdList.add(person2);

        // Act
        ChildDTO child = PersonConverter.convertToChild(person, age, householdList);

        // Assert
        assertThat(child.getFirstName()).isEqualTo("toto");
        assertThat(child.getLastName()).isEqualTo("test");
        assertThat(child.getAge()).isEqualTo(age);
        assertThat(child.getHouseholdMembers()).isNotNull();
        assertThat(child.getHouseholdMembers().size()).isEqualTo(3);
    }

    @Test
    void convertToChildNull() {
        Assertions.assertNull(PersonConverter.convertToChild(null, 1, new ArrayList<>()));
        List<Person> household = new ArrayList<>();
        household.add(null);
        Assertions.assertNull(PersonConverter.convertToChild(new Person(), 1, household));
    }

    @Test
    void convertToPersonWithMedicalRecord() {
        // Arrange
        Person person = new Person("toto", "test", "1 street", "myCity", 123, "051234", "email" +
                "@test.com");
        MedicalRecord medicalRecord = new MedicalRecord("test", "", new Date(), new ArrayList<>(),
                new ArrayList<>());

        // Act
        PersonWithMedicalRecordDTO personWithMedicalRecord =
                PersonConverter.convertToPersonWithMedicalRecord(person, medicalRecord);

        // Assert
        assertThat(personWithMedicalRecord.getFirstName()).isEqualTo("toto");
        assertThat(personWithMedicalRecord.getLastName()).isEqualTo("test");
        assertThat(personWithMedicalRecord.getAge()).isEqualTo(0);
        assertThat(personWithMedicalRecord.getPhone()).isEqualTo("051234");
        assertThat(personWithMedicalRecord.getMedications()).isNotNull();
        assertThat(personWithMedicalRecord.getMedications().size()).isEqualTo(0);
        assertThat(personWithMedicalRecord.getAllergies()).isNotNull();
        assertThat(personWithMedicalRecord.getAllergies().size()).isEqualTo(0);
    }

    @Test
    void convertToPersonWithMedicalRecordNull() {
        Assertions.assertNull(PersonConverter.convertToPersonWithMedicalRecord(null, null));
        Assertions.assertNull(PersonConverter.convertToPersonWithMedicalRecord(new Person(), null));
        Assertions.assertNull(PersonConverter.convertToPersonWithMedicalRecord(null, new MedicalRecord()));
    }

    @Test
    void convertToFormattedFullName() {
        // Arrange
        String firstName = "toto";
        String lastName = "test";

        // Act
        String formattedFullName = PersonConverter.convertToFormattedFullName(firstName, lastName);

        // Assert
        assertThat(formattedFullName).isEqualTo("toto_test");
    }

    @Test
    void testConvertToFormattedFullName() {

        // Arrange
        Person person = new Person("toto", "test", "1 street", "myCity", 123, "051234", "email" +
                "@test.com");

        // Act
        String formattedFullName =
                PersonConverter.convertToFormattedFullName(person.getFirstName(), person.getLastName());

        // Assert
        assertThat(formattedFullName).isEqualTo("toto_test");
    }

    @Test
    void convertToFormattedFullNameNull() {
        Assertions.assertNull(PersonConverter.convertToFormattedFullName(null));
    }

    @Test
    void convertToNameArray() {
        // Arrange
        String formattedFullName = "toto_test";

        // Act
        String[] nameArray = PersonConverter.convertToNameArray(formattedFullName);

        // Assert
        assertThat(nameArray).isNotNull();
        assertThat(nameArray.length).isEqualTo(2);
        assertThat(nameArray[0]).isEqualTo("toto");
        assertThat(nameArray[1]).isEqualTo("test");
    }

    @Test
    void convertToPersonInfo() {
        // Arrange
        Person person = new Person("toto", "test", "1 street", "myCity", 123, "051234", "email" +
                "@test.com");
        MedicalRecord medicalRecord = new MedicalRecord("test", "", new Date(), new ArrayList<>(),
                new ArrayList<>());

        // Act
        PersonInfoDTO personInfo =
                PersonConverter.convertToPersonInfo(person, medicalRecord);

        // Assert
        assertThat(personInfo.getFirstName()).isEqualTo("toto");
        assertThat(personInfo.getLastName()).isEqualTo("test");
        assertThat(personInfo.getAge()).isEqualTo(0);
        assertThat(personInfo.getEmail()).isEqualTo("email@test.com");
        assertThat(personInfo.getMedications()).isNotNull();
        assertThat(personInfo.getMedications().size()).isEqualTo(0);
        assertThat(personInfo.getAllergies()).isNotNull();
        assertThat(personInfo.getAllergies().size()).isEqualTo(0);
    }

    @Test
    void convertToPersonInfoNull() {
        Assertions.assertNull(PersonConverter.convertToPersonInfo(null, null));
        Assertions.assertNull(PersonConverter.convertToPersonInfo(new Person(), null));
        Assertions.assertNull(PersonConverter.convertToPersonInfo(null, new MedicalRecord()));
    }
}
