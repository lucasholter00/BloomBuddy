package com.group18.BloomBuddy.IntegrationTest;

import com.group18.BloomBuddy.DataInteractors.LightInteractor;
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
public class LightInteractorTest {
    private LightInteractor lightInteractor;
    private final String TOPIC = "wioterminal/light";
    private MQTTHandler mqttHandler;

    @BeforeEach
    public void setUp() throws MqttException {
        mqttHandler = new MQTTHandler();
        lightInteractor = new LightInteractor(mqttHandler);
    }

    @AfterEach
    public void tearDown() throws MqttException {
        mqttHandler.close();
    }

    @Test
    public void checkTopicIsCorrect(){
        assertEquals(lightInteractor.getTOPIC(), TOPIC);
    }

    @Test
    public void shouldCreateLightData() throws MqttException, InterruptedException {
        double testLight = 500.0;
        mqttHandler.publish(TOPIC, String.valueOf(testLight));
        Thread.sleep(3000);
        assertEquals(500.0, lightInteractor.getLatestData());
    }
}