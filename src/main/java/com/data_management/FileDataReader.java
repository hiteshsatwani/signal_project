package com.data_management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileDataReader implements DataReader {
    private String filePath;

    public FileDataReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                PatientRecord record = parseRecord(line);
                dataStorage.addPatientData(record.getPatientId(), record.getMeasurementValue(), record.getRecordType(), record.getTimestamp());
            }
        }
    }

    private PatientRecord parseRecord(String line) {
        String[] parts = line.split(",");
        int patientId = Integer.parseInt(parts[0]);
        long timestamp = Long.parseLong(parts[1]);
        String recordType = parts[2];
        double measurementValue = Double.parseDouble(parts[3]);

        return new PatientRecord(patientId, measurementValue, recordType, timestamp);
    }
}