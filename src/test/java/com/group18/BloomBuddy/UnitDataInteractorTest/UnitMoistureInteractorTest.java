package com.group18.BloomBuddy.UnitDataInteractorTest;

import com.group18.BloomBuddy.DataInteractors.MoistureInteractor;
import com.group18.BloomBuddy.MockClasses.MockMQTTHandler;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/***
 *  * This class is a unit test that checks if the interactor works properly with the MQTTHandler
 *  It is independent of a connection to the broker.
 *  */

public class UnitMoistureInteractorTest {
    private MoistureInteractor moistureInteractor;
    private final String TOPIC = "bloombuddy/moisture";
    private MockMQTTHandler mockMqttHandler;

    @BeforeEach
    public void setUp() throws MqttException {
        mockMqttHandler = new MockMQTTHandler();
        moistureInteractor = new MoistureInteractor(mockMqttHandler);
    }

    @Test
    public void checkTopicIsCorrect() {
        assertEquals(moistureInteractor.getTOPIC(), TOPIC);
    }

    @Test
    public void shouldCreateMoistureData() throws MqttException, InterruptedException {
        float testMoisture = 50.0F;
        mockMqttHandler.publish(TOPIC, String.valueOf(testMoisture));
        Thread.sleep(3000);
        assertEquals(testMoisture, moistureInteractor.getLatestData(), 0.01);
    }
}