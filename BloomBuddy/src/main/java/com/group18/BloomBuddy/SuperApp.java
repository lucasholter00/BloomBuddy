package com.group18.BloomBuddy;


import org.bson.Document;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class SuperApp {
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    public static void main(String[] args) throws MqttException {
        App.launch(App.class, args);
    }

}
