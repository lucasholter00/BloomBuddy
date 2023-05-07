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
            SensorInteractor data = new SensorInteractor();
                while (true) {
                    sceneSwitcher.updateSensorData(data.getData(),LineChartDataType.MOISTURE);
                    sceneSwitcher.updateSensorData(data.getData(),LineChartDataType.TEMPERATURE);
                    sceneSwitcher.updateSensorData(data.getData(),LineChartDataType.HUMIDITY);
                    sceneSwitcher.updateSensorData(data.getData(),LineChartDataType.LIGHT);
                    Thread.sleep(1000);
                    System.out.println(data.getData());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch(MqttException e){
                e.printStackTrace();
            }
        });
        sensorThread.setDaemon(true);
        sensorThread.start();
    }
}
