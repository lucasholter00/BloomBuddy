package com.group18.BloomBuddy.DataInteractors;


import com.group18.BloomBuddy.MQTTHandler.MQTTHandler;
import com.group18.BloomBuddy.SensorData.TemperatureData;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class TemperatureInteractor {



    private TemperatureData latestData;

    private final String TOPIC = "bloombuddy/temperature";
    private MQTTHandler mqttHandler;
    public TemperatureInteractor(MQTTHandler mqttHandler) throws MqttException {
    }

    public float getLatestData() {
        return -1.0F;
    }

    public String getTOPIC(){
        return TOPIC;
    }

    void processMessage(String topic, MqttMessage message) {

    }
}