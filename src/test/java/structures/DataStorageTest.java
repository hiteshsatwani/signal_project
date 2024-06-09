package structures;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.structures.singleton.DataStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataStorageTest {

    private DataStorage dataStorage;

    @BeforeEach
    public void setUp() {
        // Reset the singleton instance before each test
        dataStorage = DataStorage.getInstance();
        clearDataStorage();
    }

    private void clearDataStorage() {
        // Clear existing data to ensure clean state for each test
        for (Patient patient : dataStorage.getAllPatients()) {
            dataStorage.removePatientData(patient.getId());
        }
    }

    @Test
    public void testSingletonInstance() {
        // Test that getInstance returns the same instance
        DataStorage instance1 = DataStorage.getInstance();
        DataStorage instance2 = DataStorage.getInstance();

        assertSame(instance1, instance2, "Both instances should be the same");
    }

    @Test
    public void testAddAndRetrievePatientData() {
        int patientId = 1;
        double measurementValue = 120.5;
        String recordType = "BloodPressure";
        long timestamp = System.currentTimeMillis();

        dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);

        List<PatientRecord> records = dataStorage.getRecords(patientId, timestamp - 1000, timestamp + 1000);
        assertFalse(records.isEmpty(), "Records should not be empty");
        assertEquals(1, records.size(), "There should be exactly one record");

        PatientRecord record = records.get(0);
        assertEquals(patientId, record.getPatientId(), "Patient ID should match");
        assertEquals(measurementValue, record.getMeasurementValue(), "Measurement value should match");
        assertEquals(recordType, record.getRecordType(), "Record type should match");
        assertEquals(timestamp, record.getTimestamp(), "Timestamp should match");
    }

    @Test
    public void testGetAllPatients() {
        int patientId1 = 1;
        int patientId2 = 2;
        long timestamp = System.currentTimeMillis();

        dataStorage.addPatientData(patientId1, 120.5, "BloodPressure", timestamp);
        dataStorage.addPatientData(patientId2, 98.6, "Temperature", timestamp);

        List<Patient> allPatients = dataStorage.getAllPatients();
        assertEquals(2, allPatients.size(), "There should be exactly two patients");

        Patient patient1 = allPatients.stream().filter(p -> p.getId() == patientId1).findFirst().orElse(null);
        Patient patient2 = allPatients.stream().filter(p -> p.getId() == patientId2).findFirst().orElse(null);

        assertNotNull(patient1, "Patient 1 should not be null");
        assertNotNull(patient2, "Patient 2 should not be null");
    }
}