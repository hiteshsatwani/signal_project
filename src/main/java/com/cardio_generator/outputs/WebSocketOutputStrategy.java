package com.cardio_generator.outputs;

import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.concurrent.CopyOnWriteArraySet;

public class WebSocketOutputStrategy implements OutputStrategy {

    private WebSocketServer server;
    private final CopyOnWriteArraySet<WebSocket> connections;

    public WebSocketOutputStrategy(int port) {
        connections = new CopyOnWriteArraySet<>();
        server = new SimpleWebSocketServer(new InetSocketAddress(port), connections);
        System.out.println("WebSocket server created on port: " + port + ", listening for connections...");
        server.start();
    }

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
        // Broadcast the message to all connected clients
        for (WebSocket conn : connections) {
            conn.send(message);
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class SimpleWebSocketServer extends WebSocketServer {
        private final CopyOnWriteArraySet<WebSocket> connections;

        public SimpleWebSocketServer(InetSocketAddress address, CopyOnWriteArraySet<WebSocket> connections) {
            super(address);
            this.connections = connections;
        }

        @Override
        public void onOpen(WebSocket conn, org.java_websocket.handshake.ClientHandshake handshake) {
            System.out.println("New connection: " + conn.getRemoteSocketAddress());
            connections.add(conn);
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
            connections.remove(conn);
        }

        @Override
        public void onMessage(WebSocket conn, String message) {
            // Not used in this context
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {
            ex.printStackTrace();
        }

        @Override
        public void onStart() {
            System.out.println("Server started successfully");
        }
    }
}