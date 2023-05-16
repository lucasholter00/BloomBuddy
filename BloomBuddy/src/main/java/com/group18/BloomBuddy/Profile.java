package com.group18.BloomBuddy;

public class Profile {
    private SensorSettings sensorSettings;

    public Profile(SensorSettings sensorSettings) {
        this.sensorSettings = sensorSettings;

    }

    public SensorSettings getSensorSettings() {
        return sensorSettings;
    }

    public void setSensorSettings(SensorSettings sensorSettings) {
        this.sensorSettings = sensorSettings;
    }

}
