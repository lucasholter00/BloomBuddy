package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Observer;

public interface myObservable {
    void addObserver(myObserver observer);
    void removeObserver(myObserver observer);
    void notifyObservers(String arg) throws MqttException;
}
