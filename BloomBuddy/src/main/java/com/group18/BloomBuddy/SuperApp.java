package com.group18.BloomBuddy;


import org.bson.Document;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class SuperApp {
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    public static void main(String[] args) throws MqttException {

        App.launch(App.class, args);
        // Assuming the username and profileId exist in the database
       /*String username = "Felix";
        String profileId = "15b1d93d-a4c8-46ae-b6b0-10059388d3d3";
        DataBaseConnection databaseConnection = new DataBaseConnection();

        SensorSettings sensorSettings1 = new SensorSettings(1,2,3,4,5,6,7,8);

        Profile proff = new Profile(sensorSettings1, "correborre");

        databaseConnection.addProfile(proff, "Cornelia");

        // Call the getSensorSettings method to retrieve the sensor settings
        SensorSettings sensorSettings = databaseConnection.getSensorSettings(profileId);

        if (sensorSettings != null) {
            // Display the retrieved sensor settings
            System.out.println("Sensor Settings:");
            System.out.println("Temperature Lower Bound: " + sensorSettings.getTemperatureLowerBound());
            System.out.println("Temperature Upper Bound: " + sensorSettings.getTemperatureUpperBound());
            System.out.println("Humidity Lower Bound: " + sensorSettings.getHumidityLowerBound());
            System.out.println("Humidity Upper Bound: " + sensorSettings.getHumidityUpperBound());
            System.out.println("Moisture Lower Bound: " + sensorSettings.getMoistureLowerBound());
            System.out.println("Moisture Upper Bound: " + sensorSettings.getMoistureUpperBound());
            System.out.println("Light Lower Bound: " + sensorSettings.getLightLowerBound());
            System.out.println("Light Upper Bound: " + sensorSettings.getLightUpperBound());
        } else {
            System.out.println("Sensor settings not found for the specified username and profile ID.");
        }

        // Close the database connection
        databaseConnection.close();*/
    }

}
