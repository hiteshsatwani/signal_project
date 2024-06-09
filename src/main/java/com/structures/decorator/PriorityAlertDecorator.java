package com.structures.decorator;

public class PriorityAlertDecorator extends AlertDecorator {
    public PriorityAlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert);
    }

    @Override
    public void send(String message) {
        decoratedAlert.send("[PRIORITY] " + message);
    }
}