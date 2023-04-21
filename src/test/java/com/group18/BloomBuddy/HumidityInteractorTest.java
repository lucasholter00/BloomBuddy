package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HumidityInteractorTest {
    private HumidityInteractor humidityInteractor;
    private final String TOPIC = "wioterminal/humidity";
    private MQTTHandler mqttHandler;

    @BeforeEach
    public void setUp() throws MqttException {
        mqttHandler = new MQTTHandler();
        humidityInteractor = new HumidityInteractor(mqttHandler);
    }

    @Test
    public void shouldCreateHumidityData() throws MqttException, InterruptedException {
        double testHumidity = 80.0;
        mqttHandler.publish(TOPIC, String.valueOf(testHumidity));
        Thread.sleep(3000);
        assertEquals(80.0, humidityInteractor.getLatestData());
    }
}