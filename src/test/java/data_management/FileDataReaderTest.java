package data_management;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.data_management.DataStorage;
import com.data_management.FileDataReader;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileDataReaderTest {

    private Path tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        // Create a temporary file with test data
        tempFile = Files.createTempFile("patient_data", ".txt");
        try (FileWriter writer = new FileWriter(tempFile.toFile())) {
            writer.write("1,1700000000000,heartRate,72\n");
            writer.write("1,1700000001000,bloodPressure,120.0\n");
            writer.write("2,1700000002000,oxygenSaturation,98\n");
        }
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Delete the temporary file
        Files.delete(tempFile);
    }

    @Test
    public void testReadData() throws IOException {
        DataStorage dataStorage = new DataStorage();
        FileDataReader fileDataReader = new FileDataReader(tempFile.toString());

        fileDataReader.readData(dataStorage);

        List<PatientRecord> records = dataStorage.getAllPatients().stream()
                .flatMap(patient -> patient.getRecords(1700000000000L, 1800000000000L).stream())
                .toList();

        assertEquals(3, records.size());

        PatientRecord record1 = records.get(0);
        assertEquals(1, record1.getPatientId());
        assertEquals(1700000000000L, record1.getTimestamp());
        assertEquals("heartRate", record1.getRecordType());
        assertEquals(72, record1.getMeasurementValue(), 0.001);

        PatientRecord record2 = records.get(1);
        assertEquals(1, record2.getPatientId());
        assertEquals(1700000001000L, record2.getTimestamp());
        assertEquals("bloodPressure", record2.getRecordType());
        assertEquals(120.0, record2.getMeasurementValue(), 0.001);

        PatientRecord record3 = records.get(2);
        assertEquals(2, record3.getPatientId());
        assertEquals(1700000002000L, record3.getTimestamp());
        assertEquals("oxygenSaturation", record3.getRecordType());
        assertEquals(98, record3.getMeasurementValue(), 0.001);
    }
}