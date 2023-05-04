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
                SensorData data = new SensorData(client.getMoistureReading(), 0, client.getLightReading(), 0);
                Thread.sleep(100);
                System.out.println(data);
            }
        }
        catch(MqttException e){
            e.printStackTrace();
        }

        //Test for thresholds:

        /*SensorData test = new SensorData(30, 30, 30, 30);
        SensorSettings sensorSettings = new SensorSettings(40, 50, 40, 70, 40, 800, 40, 60);
        List<Boolean> outOfBounds = sensorSettings.checkSensorReadings(test);
        if (outOfBounds.get(0)) {
            System.out.println("Temperature out of bounds");
        }
        if (outOfBounds.get(1)) {
            System.out.println("moisture out of bounds");
        }
        if (outOfBounds.get(2)) {
            System.out.println("light intensity out of bounds");
        }
        if (outOfBounds.get(3)) {
            System.out.println("Humidity out of bounds");
        }*/
    }
}
