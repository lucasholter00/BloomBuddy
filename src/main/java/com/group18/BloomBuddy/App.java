package com.group18.BloomBuddy;

import com.group18.BloomBuddy.MQTTHandler.MQTTHandler;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Hello world!
 *
 */
public class App {
    
    public static void main( String[] args ){
        try{
            MQTTHandler client = new MQTTHandler();
            client.publish("hej", "bla4123bla");
            client.close(); 
        }
        catch(MqttException e){
            e.printStackTrace();
        }
    }
}
