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
        this.data = new SensorData();
        this.client.subscribe("BloomBuddy/Moisture/raw");
        this.client.subscribe("BloomBuddy/Light/raw");
        this.client.subscribe("BloomBuddy/Humidity/raw");
        this.client.subscribe("BloomBuddy/Temperature/raw");
    }

    private MQTTHandler createMQTTHandler() throws MqttException{
        MqttCallback mqttCallback = new MqttCallback() {
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost: " + cause.getMessage());
            }

            public void messageArrived(String topic, MqttMessage message) throws Exception {
                
                if(topic.equals("BloomBuddy/Moisture/raw")){
                    data.setMoistureLevel(Float.parseFloat(new String(message.getPayload())));
                }
                else if(topic.equals("BloomBuddy/Light/raw")){
                    data.setLightIntensity(Float.parseFloat(new String(message.getPayload())));
                }
                else if(topic.equals("BloomBuddy/Humidity/raw")){
                    data.setHumidity(Float.parseFloat(new String(message.getPayload())));
                }
                else if(topic.equals("BloomBuddy/Temperature/raw")){
                    data.setTemperature(Float.parseFloat(new String(message.getPayload())));
                }
                else{
                    System.out.println("Unknown topic: " + topic);
                }
            }

            public void deliveryComplete(IMqttDeliveryToken token) {
                // not used in this example
            }
        };
        return new MQTTHandler(mqttCallback, "BloomBuddySensorInteractor");
    }

    public SensorData getData(){
        return this.data;
    }

}
