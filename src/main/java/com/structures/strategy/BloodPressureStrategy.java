package com.structures.strategy;

import com.data_management.PatientRecord;

public class BloodPressureStrategy implements AlertStrategy {
    private static final double CRITICAL_THRESHOLD = 180.0;

    @Override
    public boolean checkAlert(PatientRecord record) {
        if ("bloodPressure".equals(record.getRecordType())) {
            return record.getMeasurementValue() > CRITICAL_THRESHOLD;
        }
        return false;
    }
}
