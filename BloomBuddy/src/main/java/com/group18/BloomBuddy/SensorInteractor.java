package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SensorInteractor {
    private MQTTHandler client;
    private SensorData data;

    public SensorInteractor() throws MqttException{
        this.client = createMQTTHandler(); 
        this.data = new SensorData(0,0,0,0);
        this.client.subscribe("BloomBuddy/Moisture/raw");
        this.client.subscribe("BloomBuddy/Light/raw");
        this.client.subscribe("BloomBuddy/Humidity/raw");
    }

    private MQTTHandler createMQTTHandler() throws MqttException{
        MqttCallback mqttCallback = new MqttCallback() {
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost: " + cause.getMessage());
            }

            public void messageArrived(String topic, MqttMessage message) throws Exception {
                System.out.println("Message received on topic " + topic + ": " + new String(message.getPayload()));
                // process the message here
            }

            public void deliveryComplete(IMqttDeliveryToken token) {
                // not used in this example
            }
        };
        return new MQTTHandler(mqttCallback);
    }

    public SensorData getData(){
        return this.data;
    }

}
