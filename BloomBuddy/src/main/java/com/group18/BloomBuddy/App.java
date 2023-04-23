package com.group18.BloomBuddy;

import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Hello world!
 *
 */
public class App {
    
    public static void main( String[] args) throws InterruptedException{
        try{
            
            MQTTHandler client = new MQTTHandler();
            while(true){
                SensorData data = new SensorData(client.getMoistureReading(), 0, 0, 0);
                Thread.sleep(1000);
            }
        }
        catch(MqttException e){
            e.printStackTrace();
        }
    }
}
