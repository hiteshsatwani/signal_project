package com.structures.strategy;

import com.data_management.PatientRecord;

public class HeartRateStrategy implements AlertStrategy {
    private static final double LOWER_THRESHOLD = 40.0;
    private static final double UPPER_THRESHOLD = 100.0;

    @Override
    public boolean checkAlert(PatientRecord record) {
        if ("heartRate".equals(record.getRecordType())) {
            return record.getMeasurementValue() < LOWER_THRESHOLD || record.getMeasurementValue() > UPPER_THRESHOLD;
        }
        return false;
    }
}
