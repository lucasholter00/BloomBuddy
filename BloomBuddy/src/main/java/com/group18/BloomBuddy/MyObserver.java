package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface MyObserver {
    void update(MyObservable o, Object arg) throws MqttException;

}
