package data_management;

import static org.junit.jupiter.api.Assertions.*;

import com.data_management.DataReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.PatientRecord;

import java.io.IOException;
import java.util.List;

class DataStorageTest {
    private DataStorage storage;

    @BeforeEach
    void setUp() throws IOException {
        // Initialize with mock data reader
        DataReader reader = new MockDataReader();
        storage = new DataStorage();
        reader.readData(storage);
    }

    @Test
    void testAddAndGetRecords() {
        // Add additional patient data directly for testing
        storage.addPatientData(1, 150.0, "WhiteBloodCells", 1714376789052L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789052L);
        assertEquals(3, records.size()); // Check if three records are retrieved

        // Validate first record
        assertEquals(100.0, records.get(0).getMeasurementValue(), 0.001);
        assertEquals(1714376789050L, records.get(0).getTimestamp());
        assertEquals("WhiteBloodCells", records.get(0).getRecordType());

        // Validate second record
        assertEquals(200.0, records.get(1).getMeasurementValue(), 0.001);
        assertEquals(1714376789051L, records.get(1).getTimestamp());
        assertEquals("WhiteBloodCells", records.get(1).getRecordType());

        // Validate third record
        assertEquals(150.0, records.get(2).getMeasurementValue(), 0.001);
        assertEquals(1714376789052L, records.get(2).getTimestamp());
        assertEquals("WhiteBloodCells", records.get(2).getRecordType());
    }
}
