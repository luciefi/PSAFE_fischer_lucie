package com.openclassrooms.safetyNet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetyNet.model.FireStation;
import com.openclassrooms.safetyNet.model.MedicalRecord;
import com.openclassrooms.safetyNet.model.Person;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.file.Paths;
import java.util.List;

@Component
@Data
public class DataSource {
    private List<Person> persons;
    private List<MedicalRecord> medicalrecords;
    private List<FireStation> firestations;

    @PostConstruct
    private void initDataSourcePostConstruct() {
        initDataSource();
    }

    private void initDataSource() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            DataSource dataSource = mapper.readValue(Paths.get("./data.json").toFile(), DataSource.class);
            persons = dataSource.persons;
            medicalrecords = dataSource.medicalrecords;
            firestations = dataSource.firestations;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
