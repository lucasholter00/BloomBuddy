package com.group18.BloomBuddy;

public class Profile {
    private SensorSettings sensorSettings;
    private String name;

    public Profile(SensorSettings sensorSettings, String name) {
        this.sensorSettings = sensorSettings;
        this.name = name;

    }

    public SensorSettings getSensorSettings() {
        return sensorSettings;
    }

    public void setSensorSettings(SensorSettings sensorSettings) {
        this.sensorSettings = sensorSettings;
    }

}
