package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface MyObserver {
    void update(MyObservable subject, Object arg) throws MqttException;

}
