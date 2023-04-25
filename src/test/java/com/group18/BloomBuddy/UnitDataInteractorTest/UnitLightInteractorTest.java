package com.group18.BloomBuddy.UnitDataInteractorTest;

import com.group18.BloomBuddy.DataInteractors.LightInteractor;
import com.group18.BloomBuddy.MockClasses.MockMQTTHandler;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/***
 *  * This class is a unit test that checks if the interactor works properly with the MQTTHandler
 *  It is independent of a connection to the broker.
 *  */

public class UnitLightInteractorTest {
    private LightInteractor lightInteractor;
    private final String TOPIC = "bloombuddy/light";
    private MockMQTTHandler mockMqttHandler;

    @BeforeEach
    public void setUp() throws MqttException {
        mockMqttHandler = new MockMQTTHandler();
        lightInteractor = new LightInteractor(mockMqttHandler);
    }

    @Test
    public void checkTopicIsCorrect(){
        assertEquals(lightInteractor.getTOPIC(), TOPIC);
    }

    @Test
    public void shouldCreateLightData() throws MqttException, InterruptedException {
        float testLight = 0.2F;
        mockMqttHandler.publish(TOPIC, String.valueOf(testLight));
        Thread.sleep(3000);
        assertEquals(testLight, lightInteractor.getLatestData(), 0.01);
    }
}
