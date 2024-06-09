package com.structures.strategy;

import com.data_management.PatientRecord;

public class HealthMonitor {
    private AlertStrategy strategy;

    public void setStrategy(AlertStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean checkForAlert(PatientRecord record) {
        if (strategy == null) {
            throw new IllegalStateException("Alert strategy not set");
        }
        return strategy.checkAlert(record);
    }
}
