package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Observable;

public interface myObserver {
    void update(myObservable o, Object arg) throws MqttException;

}
