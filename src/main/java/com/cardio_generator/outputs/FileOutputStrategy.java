package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements the {@link OutputStrategy} interface to write patient data to files.
 * This strategy uses a base directory to store files, and each unique data label
 * results in the creation of a separate file for that type of data.
 *
 * Usage:
 * This class is designed to handle output operations for patient data where each set of data
 * identified by a unique label is written to its own file within the specified base directory.
 *
 * Example:
 * <pre>
 * OutputStrategy fileOutput = new FileOutputStrategy("path/to/directory");
 * fileOutput.output(12345, System.currentTimeMillis(), "heartRate", "72 bpm");
 * </pre>
 */
public class FileOutputStrategy implements OutputStrategy {

    private String baseDirectory;

    public final ConcurrentHashMap<String, String> FILE_MAP = new ConcurrentHashMap<>();

    /**
     * Constructs a new {@code FileOutputStrategy} with the specified base directory.
     *
     * @param baseDirectory The directory path where output files will be created and stored.
     */
    public FileOutputStrategy(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    /**
     * Outputs the specified data for a patient to a file determined by the data label.
     * Each data type (label) is written to a separate file. Files and directories are created
     * if they do not exist.
     *
     * @param patientId The ID of the patient whose data is being recorded.
     * @param timestamp The timestamp of the data point, typically the time when the data was recorded.
     * @param label The type of data being recorded, e.g., "heartRate". This label determines the file name.
     * @param data The actual data to write, e.g., "72 bpm".
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }

        String filePath = FILE_MAP.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}
