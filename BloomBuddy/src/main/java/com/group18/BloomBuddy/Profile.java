package com.group18.BloomBuddy;

import java.util.ArrayList;
import java.util.UUID;

public class Profile {
    private SensorSettings sensorSettings;
    private String name;
    private String id;
    private ArrayList<HistoricalData> historicalData;

    public Profile(SensorSettings sensorSettings, String name) {
        this.sensorSettings = sensorSettings;
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.historicalData = new ArrayList<HistoricalData>();
    }

    public void addHistoricalData(HistoricalData data) {
        this.historicalData.add(data);
    }

    public SensorSettings getSensorSettings() {
        return sensorSettings;
    }

    public void setSensorSettings(SensorSettings sensorSettings) {
        this.sensorSettings = sensorSettings;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setName(String newName) {
        this.name = newName;
    }

}
