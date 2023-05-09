package com.group18.BloomBuddy;

import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.sql.*;
/**
 * Hello world!
 *
 */
public class App {
    
    public static void main( String[] args) throws InterruptedException, SQLException{
        DataBaseConnection conn = new DataBaseConnection();
        
        if(conn.verifyLogin("Lucasholter", "admin") == true){
            System.out.println("Login successful");
        }
        else{
            System.out.println("Login failed");
        }
//        conn.getAllUsers();

        /*try{
            
            MQTTHandler client = new MQTTHandler();
            while(true){
                SensorData data = new SensorData(client.getMoistureReading(), 0, client.getLightReading(), 0);
                Thread.sleep(100);
                System.out.println(data);
            }
        }
        catch(MqttException e){
            e.printStackTrace();
        }*/
    }
}
