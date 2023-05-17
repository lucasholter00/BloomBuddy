package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class NeedsWaterObserver implements MyObserver {

    public NeedsWaterObserver(){

    }

    @Override
    public void update(MyObservable subject, Object arg) throws MqttException {
        if(subject instanceof Profile && arg instanceof String){
            String filter = (String)arg;
            if (filter.equals("needsWater")){
                notifyTerminal();
            }
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
