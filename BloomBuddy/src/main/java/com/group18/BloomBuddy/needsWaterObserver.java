package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class needsWaterObserver implements myObserver {
    @Override
    public void update(myObservable o, Object arg) throws MqttException {
        String filter = (String)arg;
        if (o instanceof Profile && filter.equals("needsWater")){
            notifyTerminal();
        }
    }

    private void notifyTerminal() throws MqttException {
        MQTTHandler mqttHandler = new MQTTHandler(MqttCallback());
        mqttHandler.publish("BloomBuddy/watering","notification");
    }

    private MqttCallback MqttCallback (){
        MqttCallback mqttCallback = new MqttCallback() {
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost: " + cause.getMessage());
            }

            public void messageArrived(String topic, MqttMessage message) throws Exception {

            }

            public void deliveryComplete(IMqttDeliveryToken token) {
                // not used in this example
            }
        };

        return mqttCallback;
    }
}
