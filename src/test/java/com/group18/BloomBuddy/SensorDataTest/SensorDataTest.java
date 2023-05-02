package com.group18.BloomBuddy.SensorDataTest;

import com.group18.BloomBuddy.SensorData.SensorData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SensorDataTest {

    @Test
    public void testTemperatureValue() {
        SensorData sensorData = new SensorData();
        sensorData.setTemperature(25.0f);
        assertEquals(25.0f, sensorData.getTemperature());
    }

    @Test
    public void testMoistureValue() {
        SensorData sensorData = new SensorData();
        sensorData.setMoisture(40.0f);
        assertEquals(40.0f, sensorData.getMoisture());
    }

    @Test
    public void testLightValue() {
        SensorData sensorData = new SensorData();
        sensorData.setLight(1000.0f);
        assertEquals(1000.0f, sensorData.getLight());
    }

    @Test
    public void testHumidityValue() {
        SensorData sensorData = new SensorData();
        sensorData.setHumidity(80.0f);
        assertEquals(80.0f, sensorData.getHumidity());
    }
}