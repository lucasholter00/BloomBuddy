package com.group18.BloomBuddy.MockMQTTHandlerTest;

import com.group18.BloomBuddy.MockClasses.MockMQTTHandler;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.assertTrue;

/***
 *
 */

public class MockMQTTHandlerTest {
    private MockMQTTHandler mockMqttHandler;
    private final String TOPIC = "mocktest/topic";

    @BeforeEach
    public void setUp() throws MqttException {
        mockMqttHandler = new MockMQTTHandler();
    }

    @Test
    public void testMockPublishAndSubscribe() throws MqttException, InterruptedException {
        String payload = "It works";
        CountDownLatch latch = new CountDownLatch(1);

        mockMqttHandler.subscribe(TOPIC, (topic, message) -> {
            if (topic.equals(TOPIC) && message.toString().equals(payload)) {
                latch.countDown();
            }
        });

        mockMqttHandler.publish(TOPIC, payload);

        boolean messageReceived = latch.await(5, TimeUnit.SECONDS);

        assertTrue(messageReceived, "Message not received");
    }
}