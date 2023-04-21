package com.group18.BloomBuddy.DataInteractors;

import com.group18.BloomBuddy.SensorData.LightData;
import com.group18.BloomBuddy.MQTTHandler.MQTTHandler;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class LightInteractor {

    private LightData latestData;
    private final String TOPIC = "bloombuddy/light";

    public LightInteractor(MQTTHandler mqttHandler) {

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
