package com.group18.BloomBuddy;


import org.eclipse.paho.client.mqttv3.MqttException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
        mqttHandler.subscribe(TOPIC);
        mqttHandler.publish(TOPIC, payload);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Thread.sleep(1000);

        String expectedOutput = "Message arrived. Topic: javatest/topic Message: It works" + System.lineSeparator();
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testPublishWithInvalidTopic() throws MqttException {
        String payload = "Payload";
        assertThrows(IllegalArgumentException.class, () -> mqttHandler.publish("", payload));
    }

    @Test
    public void testSubscribeWithInvalidTopic() throws MqttException {
        assertThrows(IllegalArgumentException.class, () -> mqttHandler.subscribe(""));
    }
}

