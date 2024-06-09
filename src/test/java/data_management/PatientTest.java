package data_management;

import static org.junit.jupiter.api.Assertions.*;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PatientTest {

    private Patient patient;

    @BeforeEach
    public void setUp() {
        patient = new Patient(1);
    }

    @Test
    public void testAddRecord() {
        patient.addRecord(120.0, "bloodPressure", 1700000000000L);

        List<PatientRecord> records = patient.getRecords(Long.MIN_VALUE, Long.MAX_VALUE);
        assertEquals(1, records.size());

        PatientRecord record = records.get(0);
        assertEquals(1, record.getPatientId());
        assertEquals(120.0, record.getMeasurementValue(), 0.001);
        assertEquals("bloodPressure", record.getRecordType());
        assertEquals(1700000000000L, record.getTimestamp());
    }

    @Test
    public void testGetRecords() {
        patient.addRecord(120.0, "bloodPressure", 1700000000000L);
        patient.addRecord(130.0, "heartRate", 1700000001000L);
        patient.addRecord(85.0, "oxygenSaturation", 1700000002000L);

        List<PatientRecord> records = patient.getRecords(1700000000000L, 1700000001500L);
        assertEquals(2, records.size());

        PatientRecord record1 = records.get(0);
        assertEquals(1, record1.getPatientId());
        assertEquals(120.0, record1.getMeasurementValue(), 0.001);
        assertEquals("bloodPressure", record1.getRecordType());
        assertEquals(1700000000000L, record1.getTimestamp());

        PatientRecord record2 = records.get(1);
        assertEquals(1, record2.getPatientId());
        assertEquals(130.0, record2.getMeasurementValue(), 0.001);
        assertEquals("heartRate", record2.getRecordType());
        assertEquals(1700000001000L, record2.getTimestamp());
    }

    @Test
    public void testGetRecordsNoMatch() {
        patient.addRecord(120.0, "bloodPressure", 1700000000000L);
        patient.addRecord(130.0, "heartRate", 1700000001000L);
        patient.addRecord(85.0, "oxygenSaturation", 1700000002000L);

        List<PatientRecord> records = patient.getRecords(1800000000000L, 1900000000000L);
        assertTrue(records.isEmpty());
    }
}