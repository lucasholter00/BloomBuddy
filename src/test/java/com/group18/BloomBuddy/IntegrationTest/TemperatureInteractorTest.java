package com.group18.BloomBuddy.IntegrationTest;

import com.group18.BloomBuddy.MQTTHandler.MQTTHandler;
import com.group18.BloomBuddy.DataInteractors.TemperatureInteractor;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/***
 *  * This class is an integration test that checks if the interactor connects properly with the broker.
 *  It is dependent on that the broker's connection is stable.
 *  */
public class TemperatureInteractorTest {
    private TemperatureInteractor temperatureInteractor;
    private final String TOPIC = "bloombuddy/temperature";
    private MQTTHandler mqttHandler;

    @BeforeEach
    public void setUp() throws MqttException {
        mqttHandler = new MQTTHandler();
        temperatureInteractor = new TemperatureInteractor(mqttHandler);
    }
    @AfterEach
    public void tearDown() throws MqttException {
        mqttHandler.close();
    }

    @Test
    public void checkTopicIsCorrect(){
        assertEquals(temperatureInteractor.getTOPIC(), TOPIC);
    }

    @Test
    public void shouldCreateTemperatureData() throws MqttException, InterruptedException {
        double testTemperature = 30.0;
        mqttHandler.publish(TOPIC, String.valueOf(testTemperature));
        Thread.sleep(3000);
        assertEquals(30.0, temperatureInteractor.getLatestData());
    }
}