package com.group18.BloomBuddy.DataInteractors;

import com.group18.BloomBuddy.MQTTHandler.MQTTHandler;

import com.group18.BloomBuddy.SensorData.MoistureData;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MoistureInteractor {
    private MoistureData latestData;
    private final String TOPIC = "bloombuddy/moisture";

    public MoistureInteractor(MQTTHandler mqttHandler) {

    }

    public double getLatestData(){
        return -1;
    }

    public String getTOPIC() {
        return TOPIC;
    }

    void processMessage(String topic, MqttMessage message) {

    }
}
