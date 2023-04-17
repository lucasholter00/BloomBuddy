package com.group18.BloomBuddy;

import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Hello world!
 *
 */
public class App {
    
    public static void main( String[] args ){
        try{

            MQTTHandler client = new MQTTHandler();
            client.subscribe("hej");
            
        }
        catch(MqttException e){
            e.printStackTrace();
        }
    }
}
