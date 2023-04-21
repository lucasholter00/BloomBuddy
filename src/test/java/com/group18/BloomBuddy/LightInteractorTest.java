package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LightInteractorTest {
    private LightInteractor lightInteractor;
    private final String TOPIC = "wioterminal/light";
    private MQTTHandler mqttHandler;

    @BeforeEach
    public void setUp() throws MqttException {
        mqttHandler = new MQTTHandler();
        lightInteractor = new LightInteractor(mqttHandler);
    }

    @Test
    public void shouldCreateLightData() throws MqttException, InterruptedException {
        double testLight = 500.0;
        mqttHandler.publish(TOPIC, String.valueOf(testLight));
        Thread.sleep(3000);
        assertEquals(500.0, lightInteractor.getLatestData());
    }
}