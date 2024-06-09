package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebSocketDataReader implements DataReader {
    private WebSocketClient client;

    @Override
    public void readData(DataStorage dataStorage, String url) {
        try {
            client = new WebSocketClient(new URI(url)) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Connected to WebSocket server");
                }

                @Override
                public void onMessage(String message) {
                    PatientRecord record = parseRecord(message);
                    dataStorage.addPatientData(record.getPatientId(), record.getMeasurementValue(), record.getRecordType(), record.getTimestamp());
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Disconnected from WebSocket server");
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }
            };
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PatientRecord parseRecord(String message) {
        String[] parts = message.split(",");
        int patientId = Integer.parseInt(parts[0]);
        long timestamp = Long.parseLong(parts[1]);
        String recordType = parts[2];
        double measurementValue = Double.parseDouble(parts[3]);

        return new PatientRecord(patientId, measurementValue, recordType, timestamp);
    }
}