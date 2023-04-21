package com.group18.BloomBuddy.IntegrationTest;

import com.group18.BloomBuddy.MQTTHandler.MQTTHandler;
import com.group18.BloomBuddy.DataInteractors.MoistureInteractor;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/***
 * /***
 *  * This class is an integration test that checks if the interactor works properly with the broker.
 *  It is dependent on that the broker's connection is stable.
 *  */

public class MoistureInteractorTest {
    private MoistureInteractor moistureInteractor;
    private final String TOPIC = "wioterminal/moisture";
    private MQTTHandler mqttHandler;

    @BeforeEach
    public void setUp() throws MqttException {
        mqttHandler = new MQTTHandler();
        moistureInteractor = new MoistureInteractor(mqttHandler);
    }

    @AfterEach
    public void tearDown() throws MqttException {
        mqttHandler.close();
    }

    @Test
    public void checkTopicIsCorrect(){
        assertEquals(moistureInteractor.getTOPIC(), TOPIC);
    }

    @Test
    public void shouldCreateMoistureData() throws MqttException, InterruptedException {
        double testMoisture = 0.6;
        mqttHandler.publish(TOPIC, String.valueOf(testMoisture));
        Thread.sleep(3000);
        assertEquals(0.6, moistureInteractor.getLatestData());
    }
}

