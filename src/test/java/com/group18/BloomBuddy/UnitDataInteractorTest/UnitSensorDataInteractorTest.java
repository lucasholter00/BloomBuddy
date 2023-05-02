package com.group18.BloomBuddy.UnitDataInteractorTest;


import com.group18.BloomBuddy.MockClasses.MockMQTTHandler;
import com.group18.BloomBuddy.SensorData.SensorData;
import com.group18.BloomBuddy.SensorData.SensorDataInteractor;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitSensorDataInteractorTest {
    private SensorDataInteractor sensorDataInteractor;
    private MockMQTTHandler mockMqttHandler;

    @BeforeEach
    public void setUp() throws Exception {
        mockMqttHandler = new MockMQTTHandler();
        sensorDataInteractor = new SensorDataInteractor(mockMqttHandler);
    }

    @Test
    public void shouldCreateSensorData() throws MqttException, InterruptedException {
        float testHumidity = 80.0F;
        float testTemperature = 25.0F;
        float testMoisture = 40.0F;
        float testLight = 1000.0F;

        mockMqttHandler.publish("bloombuddy/humidity", String.valueOf(testHumidity));
        mockMqttHandler.publish("bloombuddy/temperature", String.valueOf(testTemperature));
        mockMqttHandler.publish("bloombuddy/moisture", String.valueOf(testMoisture));
        mockMqttHandler.publish("bloombuddy/light", String.valueOf(testLight));

        // Wait for messages to arrive and be processed
        Thread.sleep(3000);

        SensorData sensorData = sensorDataInteractor.getSensorData();
        assertEquals(testHumidity, sensorData.getHumidity(), 0.01F);
        assertEquals(testTemperature, sensorData.getTemperature(), 0.01F);
        assertEquals(testMoisture, sensorData.getMoisture(), 0.01F);
        assertEquals(testLight, sensorData.getLight(), 0.01F);
    }
}