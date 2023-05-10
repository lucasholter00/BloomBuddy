package com.group18.BloomBuddy;

import com.group18.BloomBuddy.application.LineChartDataType;
import com.group18.BloomBuddy.application.SceneSwitcher;
import com.group18.BloomBuddy.application.StatsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Objects;

public class App extends Application {

    private Stage stage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(Objects.requireNonNull(getClass().getResource("/loginScene.fxml")));
        Scene scene = new Scene (root);
        stage.setScene(scene);
        stage.show();

        Thread sensorThread = new Thread(() -> {
            try {
            SensorInteractor data = new SensorInteractor();
                while (true) {
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
