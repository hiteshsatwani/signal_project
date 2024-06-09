package com.cardio_generator.generators;

import java.util.Random;
import com.cardio_generator.outputs.OutputStrategy;

/**
 * Simulates the generation of blood saturation data for multiple patients.
 * This class maintains the last known saturation values for each patient and
 * adjusts them slightly during each generation cycle to simulate real-time fluctuations.
 *
 * Usage:
 * This generator should be used in conjunction with an {@link OutputStrategy} to handle
 * the output of the generated data, such as logging it to a file or sending it over a network.
 *
 * Example:
 * <pre>
 * PatientDataGenerator saturationGenerator = new BloodSaturationDataGenerator(100);
 * saturationGenerator.generate(1, new FileOutputStrategy("output_directory"));
 * </pre>
 */
public class BloodSaturationDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random(); // Random number generator for simulating data variation
    private int[] lastSaturationValues; // Array to store the last saturation value for each patient

    /**
     * Constructs a new {@code BloodSaturationDataGenerator} with a specified number of patients.
     * Initializes the saturation values of all patients to a baseline value between 95 and 100.
     *
     * @param patientCount The total number of patients for whom data will be generated.
     */
    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }

    /**
     * Generates and outputs a new blood saturation data point for a specified patient.
     * The method simulates a slight fluctuation in saturation values and ensures they remain
     * within a realistic range (90% to 100%). The generated data is then passed to an output strategy.
     *
     * @param patientId The ID of the patient for whom data is generated.
     * @param outputStrategy The output strategy to handle the generated data.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Simulate blood saturation values
            int variation = random.nextInt(3) - 1; // -1, 0, or 1 to simulate small fluctuations
            int newSaturationValue = lastSaturationValues[patientId] + variation;

            // Ensure the saturation stays within a realistic and healthy range
            newSaturationValue = Math.min(Math.max(newSaturationValue, 90), 100);
            lastSaturationValues[patientId] = newSaturationValue;

            // Output the new saturation value
            outputStrategy.output(patientId, System.currentTimeMillis(), "Saturation",
                    newSaturationValue + "%");
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood saturation data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
