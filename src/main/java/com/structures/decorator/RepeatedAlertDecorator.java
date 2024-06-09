package com.structures.decorator;

public class RepeatedAlertDecorator extends AlertDecorator {
    public RepeatedAlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert);
    }

    @Override
    public void send(String message) {
        for (int i = 0; i < 3; i++) {  // Repeat the alert 3 times
            decoratedAlert.send(message + " (repeat " + (i + 1) + ")");
            try {
                Thread.sleep(1000);  // Wait 1 second between repeats
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}