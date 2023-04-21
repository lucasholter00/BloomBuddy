package com.group18.BloomBuddy.UnitDataInteractorTest;

import com.group18.BloomBuddy.DataInteractors.HumidityInteractor;
import com.group18.BloomBuddy.MockClasses.MockMQTTHandler;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/***
 *  * This class is a unit test that checks if the interactor works properly with the MQTTHandler
 *  It is independent of a connection to the broker.
 *  */

public class UnitHumidityInteractorTest {
    private HumidityInteractor humidityInteractor;
    private final String TOPIC = "bloombuddy/humidity";
    private MockMQTTHandler mockMqttHandler;

    @BeforeEach
    public void setUp() throws MqttException {
        mockMqttHandler = new MockMQTTHandler();
        humidityInteractor = new HumidityInteractor(mockMqttHandler);
    }

    @Test
    public void checkTopicIsCorrect(){
        assertEquals(humidityInteractor.getTOPIC(), TOPIC);
    }

    @Test
    public void shouldCreateHumidityData() throws MqttException, InterruptedException {
        double testHumidity = 0.4;
        mockMqttHandler.publish(TOPIC, String.valueOf(testHumidity));
        Thread.sleep(3000);
        assertEquals(testHumidity, humidityInteractor.getLatestData(), 0.01);
    }
}
