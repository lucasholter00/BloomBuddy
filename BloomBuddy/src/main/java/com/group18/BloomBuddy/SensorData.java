package com.group18.BloomBuddy;

public class SensorData {
    private float moisture_level;
    private float temperature;
    private float light_intensity;
    private float humidity;
    
    public SensorData(float moisture_level, float temperature, float light_intensity, float humidity) {
        this.moisture_level = moisture_level;
        this.temperature = 0;
        this.light_intensity = light_intensity;
        this.humidity = 0;
    }

    public String toString() {
        return "SensorData [moisture_level=" + moisture_level + ", temperature=" + temperature + ", light_intensity="
                + light_intensity + ", humidity=" + humidity + "]";
    }

}
