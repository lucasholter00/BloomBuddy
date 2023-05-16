package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface MyObservable {
    void addObserver(MyObserver observer);
    void removeObserver(MyObserver observer);
    void notifyObservers(String arg) throws MqttException;
}
