package com.structures.factory;

public class BloodPressureAlert implements Alert {
    private String patientId;
    private String condition;
    private long timestamp;

    public BloodPressureAlert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    @Override
    public void triggerAlert() {
        System.out.println("Blood Pressure Alert for patient " + patientId + " at " + timestamp + ": " + condition);
    }
}
