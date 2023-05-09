package com.group18.BloomBuddy;

import java.util.List;
import java.util.Scanner;

public class SuperApp {
    public static void main(String[] args) {
        SensorSettings defaultSettings = new SensorSettings(20, 40, 20, 70, 10, 800, 10, 60);
        Profile profile = new Profile(defaultSettings);

        Scanner scanner = new Scanner(System.in);

        String[] labels = {
                "Temperature Lower Bound:",
                "Temperature Upper Bound:",
                "Moisture Lower Bound:",
                "Moisture Upper Bound:",
                "Light Lower Bound:",
                "Light Upper Bound:",
                "Humidity Lower Bound:",
                "Humidity Upper Bound:"
        };

        float[] newThresholds = new float[8];

        System.out.println("Enter new thresholds:");

        for (int i = 0; i < labels.length; i++) {
            boolean validInput = false;
            while (!validInput) {
                System.out.print(labels[i] + " ");
                String input = scanner.next();
                try {
                    newThresholds[i] = Float.parseFloat(input);
                    validInput = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input, please enter a valid number.");
                }
            }
        }

        scanner.close();

        SensorSettings newSettings = new SensorSettings(newThresholds[0], newThresholds[1], newThresholds[2], newThresholds[3],
                newThresholds[4], newThresholds[5], newThresholds[6], newThresholds[7]);
        profile.setSensorSettings(newSettings);


        List<Boolean> outOfBounds = profile.getSensorSettings().checkSensorReadings(new SensorData());

        if (outOfBounds.get(0)) {
            System.out.println("Temperature out of bounds");
        }
        if (outOfBounds.get(1)) {
            System.out.println("Moisture out of bounds");
        }
        if (outOfBounds.get(2)) {
            System.out.println("Light intensity out of bounds");
        }
        if (outOfBounds.get(3)) {
            System.out.println("Humidity out of bounds");
        }
    }

/*
       App.launch(App.class, args);
       */
}
