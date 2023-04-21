package com.group18.BloomBuddy.DataInteractors;

import com.group18.BloomBuddy.MQTTHandler.MQTTHandler;
import com.group18.BloomBuddy.SensorData.HumidityData;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class HumidityInteractor {

    private HumidityData latestData;

    private final String TOPIC = "bloombuddy/humidity";

    public HumidityInteractor(MQTTHandler mqttHandler) {

    }

    public String getTOPIC() {
        return TOPIC;
    }

    public double getLatestData(){
        return -1;
    }

    void processMessage(String topic, MqttMessage message) {

    }
}
