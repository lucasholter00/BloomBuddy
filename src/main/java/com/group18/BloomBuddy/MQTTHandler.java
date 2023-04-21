package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.*;

public class MQTTHandler {
    
    private final String BROKERURL ="tcp://broker.hivemq.com:1883" ;
    private final String CLIENTID = "JavaMQTTClient";
    private final int QOS = 0;
    MqttClient client; 
    
    public MQTTHandler() throws MqttException{
        initiateMQTTClient();
    }




    public void initiateMQTTClient() throws MqttException{
        this.client = new MqttClient(BROKERURL, CLIENTID);
            
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                System.out.println("Message arrived. Topic: " + topic + " Message: " + message.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("Delivery complete");
            }
        });   
        
        System.out.println("Connecting to broker: " + BROKERURL);
        client.connect(options);
        System.out.println("Connected");
    }
    

    public void publish(String topic, String payload) throws MqttException{
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(QOS);
        client.publish(topic, message);
    }

    public void subscribe(String topic, IMqttMessageListener messageListener) throws MqttException {
        this.client.subscribe(topic, messageListener);
    }

    public void close() throws MqttException{
        client.disconnect();
        client.close();
    }

}

