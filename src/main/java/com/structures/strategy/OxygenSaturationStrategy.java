package com.structures.strategy;

import com.data_management.PatientRecord;

public class OxygenSaturationStrategy implements AlertStrategy {
    private static final double CRITICAL_THRESHOLD = 90.0;

    @Override
    public boolean checkAlert(PatientRecord record) {
        if ("oxygenSaturation".equals(record.getRecordType())) {
            return record.getMeasurementValue() < CRITICAL_THRESHOLD;
        }
        return false;
    }
}
