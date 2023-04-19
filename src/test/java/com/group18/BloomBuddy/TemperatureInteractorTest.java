package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TemperatureInteractorTest {
    private TemperatureInteractor temperatureInteractor;
    private final String TOPIC = "wioterminal/temperature";
    private MQTTHandler mqttHandler;


    @BeforeEach
    public void setUp() throws MqttException {

        mqttHandler = new MQTTHandler();
        temperatureInteractor = new TemperatureInteractor(mqttHandler);
    }

    @Test
    public void shouldCreateTemperatureData() throws MqttException {
        mqttHandler.publish(TOPIC, "30");

    }



}
