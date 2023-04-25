package com.group18.BloomBuddy.IntegrationTest;

import com.group18.BloomBuddy.DataInteractors.HumidityInteractor;
import com.group18.BloomBuddy.MQTTHandler.MQTTHandler;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/***
 *  * This class is an integration test that checks if the interactor works properly with the broker.
 *  It is dependent on that the broker's connection is stable.
 *  */
public class HumidityInteractorTest {
    private HumidityInteractor humidityInteractor;
    private final String TOPIC = "wioterminal/humidity";
    private MQTTHandler mqttHandler;

    @BeforeEach
    public void setUp() throws MqttException {
        mqttHandler = new MQTTHandler();
        humidityInteractor = new HumidityInteractor(mqttHandler);
    }
    @AfterEach
    public void tearDown() throws MqttException {
        mqttHandler.close();
    }

    @Test
    public void checkTopicIsCorrect(){
        assertEquals(humidityInteractor.getTOPIC(), TOPIC);
    }

    @Test
    public void shouldCreateHumidityData() throws MqttException, InterruptedException {
        float testHumidity = 80.0F;
        mqttHandler.publish(TOPIC, String.valueOf(testHumidity));
        Thread.sleep(3000);
        assertEquals(testHumidity, humidityInteractor.getLatestData());
    }
}