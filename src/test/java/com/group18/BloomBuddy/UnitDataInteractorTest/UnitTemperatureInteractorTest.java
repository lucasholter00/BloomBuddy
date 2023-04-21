package com.group18.BloomBuddy.UnitDataInteractorTest;

import com.group18.BloomBuddy.MockClasses.MockMQTTHandler;
import com.group18.BloomBuddy.DataInteractors.TemperatureInteractor;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/***
 *  * This class is a unit test that checks if the interactor works properly with the MQTTHandler
 *  It is independent of a connection to the broker.
 *  */

public class UnitTemperatureInteractorTest {
    private TemperatureInteractor temperatureInteractor;
    private final String TOPIC = "bloombuddy/temperature";
    private MockMQTTHandler mockMqttHandler;

    @BeforeEach
    public void setUp() throws MqttException {
        mockMqttHandler = new MockMQTTHandler();
        temperatureInteractor = new TemperatureInteractor(mockMqttHandler);
    }
    @Test
    public void checkTopicIsCorrect(){
        assertEquals(temperatureInteractor.getTOPIC(), TOPIC);
    }

    @Test
    public void shouldCreateTemperatureData() throws MqttException, InterruptedException {
        double testTemperature = 30.0;
        mockMqttHandler.publish(TOPIC, String.valueOf(testTemperature));
        Thread.sleep(3000);
        assertEquals(30.0, temperatureInteractor.getLatestData());
    }
}