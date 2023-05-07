package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.SensorData;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class SceneSwitcher {

private final Stage stage;

private StatsController statsController;

    public SceneSwitcher(Stage stage) {
        this.stage = stage;
    }

    public void setStatScene() throws IOException {
        URL fxmlResource = getClass().getResource("/statScene.fxml");
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(fxmlResource);
        Parent root1 = loader1.load();
        Scene loginScene = new Scene(root1,800,600);

        statsController = loader1.getController();

        stage.setTitle("BloomBuddy");
        stage.setScene(loginScene);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.show();
    }

    public void updateSensorData(SensorData data, LineChartDataType chartType) {
        Platform.runLater(() -> statsController.updateChart(data, chartType));
    }
}