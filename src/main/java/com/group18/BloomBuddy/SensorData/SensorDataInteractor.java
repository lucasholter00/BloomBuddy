package com.group18.BloomBuddy.SensorData;


import com.group18.BloomBuddy.MQTTHandler.MQTTHandler;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;

public class SensorDataInteractor {

    private SensorData sensorData;

    public SensorDataInteractor(MQTTHandler mqttHandler) throws Exception {

    }

    private IMqttMessageListener createMessageListener(SensorType sensorType) {
        return null;
    }

    public SensorData getSensorData() {
        return null;
    }
}