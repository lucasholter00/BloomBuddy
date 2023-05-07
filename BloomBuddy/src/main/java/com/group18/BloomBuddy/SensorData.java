package com.group18.BloomBuddy;

import java.util.Random;

public class SensorData {
    private float moistureLevel;
    private float temperature;
    private float lightIntensity;
    private float humidity;
    
    public SensorData(float moisture_level, float temperature, float light_intensity, float humidity) {
        this.moisture_level = new Random().nextInt(100);
        this.temperature = new Random().nextInt(100);
        this.light_intensity = new Random().nextInt(100);
        this.humidity = new Random().nextInt(100);
    }

    public String toString() {
        return "SensorData [moisture_level=" + moistureLevel + ", temperature=" + temperature + ", light_intensity="
                + lightIntensity + ", humidity=" + humidity + "]";
    }
    public void setMoistureLevel(float moisture_level){
        this.moistureLevel = moisture_level;
    }
    
    public void setTemperature(float temperature){
        this.temperature = temperature;
    }

    public void setLightIntensity(float light_intensity){
        this.lightIntensity = light_intensity;
    }

    public void setHumidity(float humidity){
        this.humidity = humidity;
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
