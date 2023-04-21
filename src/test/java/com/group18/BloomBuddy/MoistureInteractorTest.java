package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoistureInteractorTest {
    private MoistureInteractor moistureInteractor;
    private final String TOPIC = "wioterminal/moisture";
    private MQTTHandler mqttHandler;

    @BeforeEach
    public void setUp() throws MqttException {
        mqttHandler = new MQTTHandler();
        moistureInteractor = new MoistureInteractor(mqttHandler);
    }

    @Test
    public void shouldCreateMoistureData() throws MqttException, InterruptedException {
        double testMoisture = 0.6;
        mqttHandler.publish(TOPIC, String.valueOf(testMoisture));
        Thread.sleep(3000);
        assertEquals(0.6, moistureInteractor.getLatestData());
    }
}

