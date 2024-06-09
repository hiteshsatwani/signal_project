package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * This interface is used for generating patient data in health monitoring simulations.
 * Classes that implement this interface will create specific health data for patients
 * and send this data to an output strategy defined by the {@link OutputStrategy} interface.
 *
 * Usage:
 * Implement this interface in classes that need to generate different types of health data.
 * Each implementation must handle the creation of data and its subsequent management through an output method.
 *
 * Example:
 * <pre>
 * public class HeartRateDataGenerator implements PatientDataGenerator {
 *     @Override
 *     public void generate(int patientId, OutputStrategy outputStrategy) {
 *         // Implementation details: generate heart rate data and use output strategy
 *     }
 * }
 * </pre>
 */
public interface  PatientDataGenerator {
    /**
     * Generates health data for a specific patient using the given output strategy.
     * This function is designed to be used in a simulation where patient health data
     * needs to be continuously generated and processed.
     *
     * @param patientId The ID of the patient for whom data is being generated.
     *                  This ID links the generated data with a specific patient.
     * @param outputStrategy The mechanism through which the generated data is handled or stored.
     *                       This could be through file storage, network transmission, or another method
     *                       depending on the implementation of {@link OutputStrategy}.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}

