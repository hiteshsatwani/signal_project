package com.structures.decorator;

public class BasicAlert implements Alert {
    @Override
    public void send(String message) {
        System.out.println("Sending alert: " + message);
    }
}
