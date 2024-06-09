package com.structures.singleton;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStorage {
    private static DataStorage instance;
    private Map<Integer, Patient> patientMap; // Stores patient objects indexed by their unique patient ID.

    // Private constructor to prevent instantiation
    private DataStorage() {
        this.patientMap = new HashMap<>();
    }

    // Public method to provide access to the instance
    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    // Adds or updates patient data in the storage
    public synchronized void addPatientData(int patientId, double measurementValue, String recordType, long timestamp) {
        Patient patient = patientMap.get(patientId);
        if (patient == null) {
            patient = new Patient(patientId);
            patientMap.put(patientId, patient);
        }
        patient.addRecord(measurementValue, recordType, timestamp);
    }

    // Retrieves a list of PatientRecord objects for a specific patient, filtered by a time range
    public synchronized List<PatientRecord> getRecords(int patientId, long startTime, long endTime) {
        Patient patient = patientMap.get(patientId);
        if (patient != null) {
            return patient.getRecords(startTime, endTime);
        }
        return new ArrayList<>(); // return an empty list if no patient is found
    }

    // Retrieves a collection of all patients stored in the data storage
    public synchronized List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    public void removePatientData(int id) {
        patientMap.remove(id);
    }
}