package com.cardio_generator.generators;

import java.util.Random;
import com.cardio_generator.outputs.OutputStrategy;

/**
 * Simulates alert conditions for patients in a health monitoring system.
 * This class generates random alerts indicating critical conditions that may need immediate attention.
 * Alerts can be in two states: "triggered" when a new alert condition occurs and "resolved" when the condition resolves.
 */
public class AlertGenerator implements PatientDataGenerator {

    // Random generator to simulate the stochastic nature of health alerts.
    public static final Random RANDOM_GENERATOR = new Random();
    // Tracks the alert state for each patient; true if an alert is currently triggered, false if resolved.
    private boolean[] alertStates;

    /**
     * Constructs an AlertGenerator for a specified number of patients.
     *
     * @param patientCount The total number of patients to manage alert states for.
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Generates and outputs alert data for a specific patient based on random probabilities.
     * The method either resolves an existing alert or triggers a new one based on simulated conditions.
     *
     * @param patientId The patient ID for whom the alert data is to be generated.
     * @param outputStrategy The output strategy to use for dispatching the alert data.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = RANDOM_GENERATOR.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}