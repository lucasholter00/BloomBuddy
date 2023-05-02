package com.group18.BloomBuddy.IntegrationTest;

import com.group18.BloomBuddy.MQTTHandler.MQTTHandler;
import com.group18.BloomBuddy.SensorData.SensorData;
import com.group18.BloomBuddy.SensorData.SensorDataInteractor;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SensorDataInteractorTest {
    private SensorDataInteractor sensorDataInteractor;
    private MQTTHandler mqttHandler;

    @BeforeEach
    public void setUp() throws Exception {
        mqttHandler = new MQTTHandler();
        sensorDataInteractor = new SensorDataInteractor(mqttHandler);
    }

    @AfterEach
    public void tearDown() throws MqttException {
        mqttHandler.close();
    }

    @Test
    public void shouldCreateSensorData() throws MqttException, InterruptedException {
        float testHumidity = 80.0F;
        float testTemperature = 25.0F;
        float testMoisture = 40.0F;
        float testLight = 1000.0F;

        mqttHandler.publish("bloombuddy/humidity", String.valueOf(testHumidity));
        mqttHandler.publish("bloombuddy/temperature", String.valueOf(testTemperature));
        mqttHandler.publish("bloombuddy/moisture", String.valueOf(testMoisture));
        mqttHandler.publish("bloombuddy/light", String.valueOf(testLight));

        // Wait for messages to arrive and be processed
        Thread.sleep(3000);

        SensorData sensorData = sensorDataInteractor.getSensorData();
        assertEquals(testHumidity, sensorData.getHumidity());
        assertEquals(testTemperature, sensorData.getTemperature());
        assertEquals(testMoisture, sensorData.getMoisture());
        assertEquals(testLight, sensorData.getLight());
    }
}