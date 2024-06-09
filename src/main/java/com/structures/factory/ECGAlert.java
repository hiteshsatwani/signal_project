package com.structures.factory;

public class ECGAlert implements Alert {
    private String patientId;
    private String condition;
    private long timestamp;

    public ECGAlert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    @Override
    public void triggerAlert() {
        System.out.println("ECG Alert for patient " + patientId + " at " + timestamp + ": " + condition);
    }
}
