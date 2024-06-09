package com.cardio_generator.outputs;

import com.cardio_generator.outputs.OutputStrategy;

import java.util.ArrayList;
import java.util.List;

public class TestOutputStrategy implements OutputStrategy {

    private final List<String> outputs = new ArrayList<>();

    @Override
    public void output(int patientId, long timestamp, String type, String status) {
        outputs.add(patientId + "," + timestamp + "," + type + "," + status);
    }

    public List<String> getOutputs() {
        return outputs;
    }
}