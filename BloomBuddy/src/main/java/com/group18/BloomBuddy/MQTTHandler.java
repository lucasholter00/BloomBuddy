package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.UUID;

public class MQTTHandler {
    
    private final String BROKERURL ="tcp://broker.hivemq.com:1883" ;
    private String clientId;
    private final int QOS = 0;
    private MqttClient client;
    private float humidityReading;
    private float moistureReading;
    private float lightReading;
    private float temperatureReading;


    public MQTTHandler(MqttCallback callback) throws MqttException{
        this.clientId = UUID.randomUUID().toString();
        initiateMQTTClient(callback);
    }

    public void initiateMQTTClient(MqttCallback callback) throws MqttException{
        this.client = new MqttClient(BROKERURL, clientId);
            
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);

        client.setCallback(callback); 
        
        System.out.println("Connecting to broker: " + BROKERURL);
        client.connect(options);
        System.out.println("Connected");
    }
    

    public void publish(String topic, String payload) throws MqttException{
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(QOS);
        client.publish(topic, message);
    } 

    public void subscribe(String topic) throws MqttException{
        this.client.subscribe(topic);
    }

    public void close() throws MqttException{
        client.disconnect();
        client.close();
    }

}

