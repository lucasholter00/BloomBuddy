package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Hello world!
 *
 */
public class App {
    
    public static void main( String[] args) throws InterruptedException{
        try{
            SensorInteractor sensorInteractor = new SensorInteractor();
            while(true){
                Thread.sleep(1000);
                System.out.println(sensorInteractor.getData());
            }
        }
        catch(MqttException e){
            e.printStackTrace();
        }
    }
}
