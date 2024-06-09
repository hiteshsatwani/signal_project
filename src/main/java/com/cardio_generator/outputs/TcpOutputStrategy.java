package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * Implements the {@link OutputStrategy} interface to send patient data over a TCP network connection.
 * This class sets up a TCP server that listens on a specified port and waits for a client to connect.
 * Once a connection is established, patient data can be sent to the connected client.
 *
 * Usage:
 * This strategy is used in scenarios where patient data needs to be transmitted to remote locations
 * in real-time, such as monitoring stations or remote medical facilities.
 *
 * Example:
 * <pre>
 * OutputStrategy tcpOutput = new TcpOutputStrategy(5000);
 * tcpOutput.output(12345, System.currentTimeMillis(), "heartRate", "72 bpm");
 * </pre>
 */
public class TcpOutputStrategy implements OutputStrategy {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;

    /**
     * Creates a TCP server on the specified port and listens for client connections.
     * The server runs in its own thread to handle client connections without blocking the main application.
     *
     * @param port The port number on which the server will listen.
     */
    public TcpOutputStrategy(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends formatted patient data over the TCP connection to the connected client.
     * The data is sent only if a client is connected and the output stream is not null.
     *
     * @param patientId The ID of the patient.
     * @param timestamp The timestamp at which the data was recorded.
     * @param label The label identifying the type of data (e.g., "heartRate").
     * @param data The actual data value (e.g., "72 bpm").
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
