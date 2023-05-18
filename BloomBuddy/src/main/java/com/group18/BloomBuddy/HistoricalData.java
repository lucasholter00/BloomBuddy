package com.group18.BloomBuddy;

import java.time.LocalDateTime;

public class HistoricalData {

    private float moisture;
    private float temperature;
    private float humidity;
    private float light;
    private LocalDateTime time;


    public HistoricalData(float moisture, float temperature, float humidity, float light){
        this.moisture = moisture;
        this.temperature = temperature;
        this.humidity = humidity;
        this.light = light;
        this.time = LocalDateTime.now();

    }

    public HistoricalData(float moisture, float temperature, float humidity, float light, LocalDateTime time){
        this.moisture = moisture;
        this.temperature = temperature;
        this.humidity = humidity;
        this.light = light;
        this.time = time;
    }

    //Getter methods
    public float getMoisture(){
        return moisture;
    }  

    public float getTemperature(){
        return temperature;
    }

    public float getHumidity(){
        return humidity;
    }

    public float getLight(){
        return light;
    }

    public LocalDateTime getTime(){
        return time;
    }

    public String toString(){
        return "Moisture: " + moisture + " Temperature: " + temperature + " Humidity: " + humidity + " Light: " + light + " Time: " + time;
    }
    
}
