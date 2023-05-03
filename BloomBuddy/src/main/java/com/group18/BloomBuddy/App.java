package com.group18.BloomBuddy;

import com.group18.BloomBuddy.application.MainApp;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Hello world!
 *
 */
public class App {
    
    public static void main( String[] args) throws Exception {
        MainApp mainApp = new MainApp();
        try{
            
            MQTTHandler client = new MQTTHandler();
            while(true){
                SensorData data = new SensorData(client.getMoistureReading(), 0, client.getLightReading(), 0);
                Thread.sleep(100);
                System.out.println(data);
            }
        }
        catch(MqttException e){
            e.printStackTrace();
        }
    }
}
