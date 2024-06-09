package com.cardio_generator.outputs;

/**
 * Defines a strategy interface for outputting health data simulation results.
 * Implementations of this interface can direct the simulation output to various destinations
 * such as console, files, WebSockets, or TCP sockets based on the specified output method.
 *
 * Implementing classes are responsible for handling the details of how data is formatted and transmitted.
 */
public interface OutputStrategy {

    /**
     * Outputs the generated data for a patient.
     * This method should be implemented to define how and where the data should be outputted,
     * e.g., to a console, a file, or over a network connection.
     *
     * @param patientId The identifier of the patient whose data is being output.
     * @param timestamp The timestamp at which the data was recorded or generated.
     * @param label A descriptive label for the data (e.g., "ECG", "Blood Pressure").
     * @param data The actual data to be output as a string.
     */
    void output(int patientId, long timestamp, String label, String data);
}