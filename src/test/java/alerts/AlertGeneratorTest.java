package com.alerts;

import static org.junit.jupiter.api.Assertions.*;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

class AlertGeneratorTest {

    private DataStorage dataStorage;
    private AlertGenerator alertGenerator;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        dataStorage = new DataStorage();
        alertGenerator = new AlertGenerator(dataStorage);

        // Set up for capturing console output
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Add test data
        long currentTime = System.currentTimeMillis();
        Patient patient = new Patient(1);
        patient.addRecord(190.0, "bloodPressure", currentTime);
        patient.addRecord(80.0, "bloodPressure", currentTime + 1000);
        patient.addRecord(85.0, "oxygenSaturation", currentTime + 2000);
        patient.addRecord(120.0, "ECG", currentTime + 3000);
        patient.addRecord(200.0, "ECG", currentTime + 4000); // Abnormal ECG
        dataStorage.addPatientData(1, 190.0, "bloodPressure", currentTime);
        dataStorage.addPatientData(1, 80.0, "bloodPressure", currentTime + 1000);
        dataStorage.addPatientData(1, 85.0, "oxygenSaturation", currentTime + 2000);
        dataStorage.addPatientData(1, 120.0, "ECG", currentTime + 3000);
        dataStorage.addPatientData(1, 200.0, "ECG", currentTime + 4000);
    }

    @Test
    void testCriticalBloodPressureAlert() {
        Patient patient = dataStorage.getAllPatients().get(0);
        alertGenerator.evaluateData(patient);

        String output = outContent.toString();
        assertTrue(output.contains("Critical Blood Pressure"));
    }

    @Test
    void testLowBloodOxygenSaturationAlert() {
        Patient patient = dataStorage.getAllPatients().get(0);
        alertGenerator.evaluateData(patient);

        String output = outContent.toString();
        assertTrue(output.contains("Low Blood Oxygen Saturation"));
    }

    @Test
    void testHypotensiveHypoxemiaAlert() {
        Patient patient = dataStorage.getAllPatients().get(0);
        alertGenerator.evaluateData(patient);

        String output = outContent.toString();
        assertTrue(output.contains("Hypotensive Hypoxemia"));
    }

    @Test
    void testAbnormalECGDataAlert() {
        Patient patient = dataStorage.getAllPatients().get(0);
        alertGenerator.evaluateData(patient);

        String output = outContent.toString();
        assertFalse(output.contains("Abnormal ECG Data"));
    }
}