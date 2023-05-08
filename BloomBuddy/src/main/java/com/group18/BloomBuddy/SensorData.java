package com.group18.BloomBuddy;

public class SensorData {
    private float moistureLevel;
    private float temperature;
    private float lightIntensity;
    private float humidity;
    
    public SensorData() {
        this.moistureLevel = 0;
        this.temperature = 0;
        this.lightIntensity = 0;
        this.humidity = 0;
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

    public float getTemperature() {
        return this.temperature;
    }

    public float getMoistureLevel() {
        return this.moisture_level;
    }

    public float getLightIntensity() {
        return this.light_intensity;
    }

    public float getHumidity() {
        return this.humidity;
    }
}
