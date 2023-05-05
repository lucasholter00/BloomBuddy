package com.group18.BloomBuddy;

import com.group18.BloomBuddy.application.LineChartDataType;
import com.group18.BloomBuddy.application.SceneSwitcher;
import javafx.application.Application;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttException;

public class App extends Application {

    private Stage stage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        SceneSwitcher sceneSwitcher = new SceneSwitcher(stage);
        sceneSwitcher.setStatScene();
        Thread sensorThread = new Thread(() -> {
            try {
                MQTTHandler client = new MQTTHandler();
                while (true) {
                    SensorData data = new SensorData(client.getMoistureReading(), 0, client.getLightReading(), 0);
                    sceneSwitcher.updateSensorData(data,LineChartDataType.MOISTURE);
                    sceneSwitcher.updateSensorData(data,LineChartDataType.TEMPERATURE);
                    sceneSwitcher.updateSensorData(data,LineChartDataType.HUMIDITY);
                    sceneSwitcher.updateSensorData(data,LineChartDataType.LIGHT);
                    Thread.sleep(1000);
                    System.out.println(data);
                }
            } catch (MqttException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        sensorThread.setDaemon(true);
        sensorThread.start();
    }
}