package com.group18.BloomBuddy.MockClasses;

import com.group18.BloomBuddy.MQTTHandler.MQTTHandler;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 * This class simlulates the behavior of the MQTTHandler class without connecting to a broker.
 */

public class MockMQTTHandler extends MQTTHandler {

    private Map<String, IMqttMessageListener> topicListeners = new HashMap<>();


    public MockMQTTHandler() throws MqttException {
        super();
    }

    @Override
    public void initiateMQTTClient() throws MqttException {
        //do nothing
    }


    @Override
    public void publish(String topic, String payload) throws MqttException {
        IMqttMessageListener listener = topicListeners.get(topic);
        if (listener != null) {
            MqttMessage message = new MqttMessage(payload.getBytes());
            try {
                listener.messageArrived(topic, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void subscribe(String topic, IMqttMessageListener messageListener) throws MqttException {
        topicListeners.put(topic, messageListener);
    }
}