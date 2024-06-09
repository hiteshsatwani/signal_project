package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */

    public void evaluateData(Patient patient) {
        List<PatientRecord> records = patient.getRecords(Long.MIN_VALUE, Long.MAX_VALUE); // Get all records

        checkBloodPressureAlerts(patient, records);
        checkBloodSaturationAlerts(patient, records);
        checkHypotensiveHypoxemiaAlert(patient, records);
        checkECGDataAlerts(patient, records);
    }

    /**
     * Checks the patient's records for blood pressure-related alerts.
     * Triggers an alert if blood pressure is critically high or low.
     *
     * @param patient the patient whose records are being evaluated
     * @param records the list of patient records to check
     */
    /**
     * Checks the patient's records for blood pressure-related alerts.
     * Triggers an alert if blood pressure is critically high or low.
     *
     * @param patient the patient whose records are being evaluated
     * @param records the list of patient records to check
     */
    private void checkBloodPressureAlerts(Patient patient, List<PatientRecord> records) {
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("bloodPressure")) {
                if (record.getMeasurementValue() > 180.0 || record.getMeasurementValue() < 90.0) {
                    triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Critical Blood Pressure", System.currentTimeMillis()));
                }
            }
        }
    }

    /**
     * Checks the patient's records for blood oxygen saturation-related alerts.
     * Triggers an alert if blood oxygen saturation is below the safe threshold.
     *
     * @param patient the patient whose records are being evaluated
     * @param records the list of patient records to check
     */
    private void checkBloodSaturationAlerts(Patient patient, List<PatientRecord> records) {
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("oxygenSaturation")) {
                if (record.getMeasurementValue() < 92.0) {
                    triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Low Blood Oxygen Saturation", System.currentTimeMillis()));
                }
            }
        }
    }

    /**
     * Checks for combined conditions that trigger the Hypotensive Hypoxemia alert.
     * Triggers an alert if both blood pressure and blood oxygen saturation are below their respective thresholds.
     *
     * @param patient the patient whose records are being evaluated
     * @param records the list of patient records to check
     */
    private void checkHypotensiveHypoxemiaAlert(Patient patient, List<PatientRecord> records) {
        boolean lowBloodPressure = false;
        boolean lowOxygenSaturation = false;
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("bloodPressure") && record.getMeasurementValue() < 90.0) {
                lowBloodPressure = true;
            }
            if (record.getRecordType().equals("oxygenSaturation") && record.getMeasurementValue() < 92.0) {
                lowOxygenSaturation = true;
            }
        }
        if (lowBloodPressure && lowOxygenSaturation) {
            triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Hypotensive Hypoxemia", System.currentTimeMillis()));
        }
    }

    /**
     * Checks the patient's records for ECG-related alerts.
     * Triggers an alert if the ECG data shows significant abnormalities compared to the average.
     *
     * @param patient the patient whose records are being evaluated
     * @param records the list of patient records to check
     */
    private void checkECGDataAlerts(Patient patient, List<PatientRecord> records) {
        List<PatientRecord> ecgRecords = records.stream()
                .filter(record -> record.getRecordType().equals("ECG"))
                .collect(Collectors.toList());

        if (ecgRecords.isEmpty()) {
            return;
        }

        double averageECG = ecgRecords.stream()
                .mapToDouble(PatientRecord::getMeasurementValue)
                .average()
                .orElse(Double.NaN);

        for (PatientRecord record : ecgRecords) {
            if (record.getMeasurementValue() > averageECG * 1.5) { // Simplified condition
                triggerAlert(new Alert(String.valueOf(patient.getPatientId()), "Abnormal ECG Data", System.currentTimeMillis()));
            }
        }
    }



    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        // This can be extended to notify medical staff, log the alert, etc.
        System.out.println("ALERT: Patient ID: " + alert.getPatientId() +
                ", Condition: " + alert.getCondition() +
                ", Timestamp: " + alert.getTimestamp());
    }
}
