package com.group18.BloomBuddy;


import org.eclipse.paho.client.mqttv3.MqttException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class MQTTHandlerTest {
    private MQTTHandler mqttHandler;
    private final String TOPIC = "javatest/topic";
    @BeforeEach
    public void setUp() throws MqttException {
        mqttHandler = new MQTTHandler();
    }

    @AfterEach
    public void tearDown() throws MqttException {
        mqttHandler.close();
    }

    @Test
    public void testInitiateMQTTClient() throws MqttException {
        assertDoesNotThrow(() -> mqttHandler.initiateMQTTClient());
    }


    @Test
    public void testPublishAndSubscribe() throws MqttException, InterruptedException {
        String payload = "It works";
        AtomicBoolean messageReceived = new AtomicBoolean(false);
        int maxAttempts = 5;
        int attempt = 0;

        while (!messageReceived.get() && attempt < maxAttempts) {
            mqttHandler.subscribe(TOPIC, (topic, message) -> {
                if (topic.equals(TOPIC) && message.toString().equals(payload)) {
                    messageReceived.set(true);
                }
            });

            mqttHandler.publish(TOPIC, payload);
            attempt++;

            // Wait for the message to be received or for a timeout
            for (int i = 0; i < 30 && !messageReceived.get(); i++) {
                Thread.sleep(100);
            }
        }

        assertTrue(messageReceived.get(), "Message not received after " + maxAttempts + " attempts");
    }

    @Test
    public void testPublishWithInvalidTopic() throws MqttException {
        String payload = "Payload";
        assertThrows(IllegalArgumentException.class, () -> mqttHandler.publish("", payload));
    }

    @Test
    public void testSubscribeWithInvalidTopic() throws MqttException {
        assertThrows(IllegalArgumentException.class, () -> mqttHandler.subscribe("", null));
    }
}

