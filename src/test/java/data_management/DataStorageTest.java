package data_management;

import static org.junit.jupiter.api.Assertions.*;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class DataStorageTest {

    private DataStorage dataStorage;

    @BeforeEach
    void setUp() {
        dataStorage = new DataStorage();
    }

    @Test
    void testAddPatientData() {
        int patientId = 1;
        double measurementValue = 72.0;
        String recordType = "heartRate";
        long timestamp = System.currentTimeMillis();

        dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);

        List<PatientRecord> records = dataStorage.getRecords(patientId, 0, System.currentTimeMillis());
        assertEquals(1, records.size());
        assertEquals(measurementValue, records.get(0).getMeasurementValue());
        assertEquals(recordType, records.get(0).getRecordType());
        assertEquals(timestamp, records.get(0).getTimestamp());
    }

    @Test
    void testUpdatePatientData() {
        int patientId = 1;
        double initialMeasurementValue = 72.0;
        String recordType = "heartRate";
        long timestamp1 = System.currentTimeMillis();

        dataStorage.addPatientData(patientId, initialMeasurementValue, recordType, timestamp1);

        double updatedMeasurementValue = 75.0;
        long timestamp2 = System.currentTimeMillis();

        dataStorage.addPatientData(patientId, updatedMeasurementValue, recordType, timestamp2);

        List<PatientRecord> records = dataStorage.getRecords(patientId, 0, System.currentTimeMillis());
        assertEquals(2, records.size());

        // Ensure the first record is the initial value
        assertEquals(initialMeasurementValue, records.get(0).getMeasurementValue());
        assertEquals(timestamp1, records.get(0).getTimestamp());

        // Ensure the second record is the updated value
        assertEquals(updatedMeasurementValue, records.get(1).getMeasurementValue());
        assertEquals(timestamp2, records.get(1).getTimestamp());
    }

    @Test
    void testGetRecordsByTimeRange() {
        int patientId = 1;
        String recordType = "heartRate";
        long startTime = System.currentTimeMillis();

        dataStorage.addPatientData(patientId, 70.0, recordType, startTime);
        dataStorage.addPatientData(patientId, 75.0, recordType, startTime + 1000);
        dataStorage.addPatientData(patientId, 80.0, recordType, startTime + 2000);

        long endTime = startTime + 1500;

        List<PatientRecord> records = dataStorage.getRecords(patientId, startTime, endTime);
        assertEquals(2, records.size());

        // Ensure the retrieved records are within the time range
        assertTrue(records.stream().allMatch(record -> record.getTimestamp() >= startTime && record.getTimestamp() <= endTime));
    }

    @Test
    void testGetAllPatients() {
        int patientId1 = 1;
        int patientId2 = 2;

        dataStorage.addPatientData(patientId1, 70.0, "heartRate", System.currentTimeMillis());
        dataStorage.addPatientData(patientId2, 75.0, "bloodPressure", System.currentTimeMillis());

        List<Patient> patients = dataStorage.getAllPatients();
        assertEquals(2, patients.size());

        // Ensure all patients are correctly retrieved
        assertTrue(patients.stream().anyMatch(patient -> patient.getPatientId() == patientId1));
        assertTrue(patients.stream().anyMatch(patient -> patient.getPatientId() == patientId2));
    }
}