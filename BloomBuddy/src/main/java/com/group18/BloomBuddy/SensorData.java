package com.group18.BloomBuddy;

import java.util.Random;

public class SensorData {
    private float moisture_level;
    private float temperature;
    private float light_intensity;
    private float humidity;
    
    public SensorData(float moisture_level, float temperature, float light_intensity, float humidity) {
        this.moisture_level = new Random().nextInt(100);
        this.temperature = new Random().nextInt(100);
        this.light_intensity = new Random().nextInt(100);
        this.humidity = new Random().nextInt(100);
    }

    public String toString() {
        return "SensorData [moisture_level=" + moisture_level + ", temperature=" + temperature + ", light_intensity="
                + light_intensity + ", humidity=" + humidity + "]";
    }

    public float getHumidity() {
        return this.humidity;
    }
    public float getLight_intensity() {
        return this.light_intensity;
    }
    public float getMoisture_level() {
        return this.moisture_level;
    }
    public float getTemperature() {
        return this.temperature;
    }
}
