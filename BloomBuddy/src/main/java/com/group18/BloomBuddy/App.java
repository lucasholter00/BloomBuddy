package com.group18.BloomBuddy;

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
                SensorData data = new SensorData(client.getMoistureReading(), 0, 0, client.getHumidityReading());
                Thread.sleep(100);
                System.out.println(data);
            }
        }
        catch(MqttException e){
            e.printStackTrace();
        }
    }
}
