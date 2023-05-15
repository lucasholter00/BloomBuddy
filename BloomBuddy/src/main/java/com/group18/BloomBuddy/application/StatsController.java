package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.SensorData;
import com.group18.BloomBuddy.SensorInteractor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatsController extends SceneSwitcher {

    @FXML
    private LineChart<String, Number> tempLineChart;
    @FXML
    private LineChart<String, Number> moistLineChart;
    @FXML
    private LineChart<String, Number> humLineChart;
    @FXML
    private LineChart<String, Number> lightLineChart;

    private XYChart.Series<String, Number> tempSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> moistSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> humSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> lightSeries = new XYChart.Series<>();
    @FXML
    public void initialize() {
        initializeChart(tempLineChart);
        initializeChart(moistLineChart);
        initializeChart(humLineChart);
        initializeChart(lightLineChart);
        startSensorThread();
        initializeSeries();
    }

    public void show(Stage stage) throws IOException {
        URL fxmlResource = getClass().getResource("/statScene.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlResource);
        Parent root = loader.load();
        Scene statScene = new Scene(root, 800, 600);
        stage.setScene(statScene);
        loader.getController();

        stage.setTitle("BloomBuddy");
        stage.setScene(statScene);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.show();
    }

    public void startSensorThread() {
        Thread sensorThread = new Thread(() -> {
            try {
                SensorInteractor data = new SensorInteractor();
                while (true) {
                    Thread.sleep(1000);
                    System.out.println(data.getData());
                    Platform.runLater(() -> {
                        updateChart(data.getData(), LineChartDataType.LIGHT);
                        updateChart(data.getData(), LineChartDataType.HUMIDITY);
                        updateChart(data.getData(), LineChartDataType.TEMPERATURE);
                        updateChart(data.getData(), LineChartDataType.MOISTURE);
                    });
                }
            } catch (InterruptedException | MqttException e) {
                e.printStackTrace();
            }
        });
        sensorThread.setDaemon(true);
        sensorThread.start();
    }

    public void initializeSeries() {
        tempLineChart.getData().add(tempSeries);
        moistLineChart.getData().add(moistSeries);
        humLineChart.getData().add(humSeries);
        lightLineChart.getData().add(lightSeries);
    }
    public void initializeChart(LineChart<String, Number> lineChart) {
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);
        lineChart.getXAxis().setTickLabelsVisible(true);
        lineChart.getYAxis().setTickLabelsVisible(true);
        lineChart.getXAxis().setAutoRanging(true);
        lineChart.getYAxis().setAutoRanging(true);
        lineChart.setVerticalGridLinesVisible(true);
        lineChart.setHorizontalGridLinesVisible(true);
        lineChart.getXAxis().setLabel("Time");

        //Checks the id, to which the enum value is assigned to the chartType.
        LineChartDataType chartType = null;
        switch (lineChart.getId()) {
            case "tempLineChart":
                chartType = LineChartDataType.TEMPERATURE;
                break;
            case "moistLineChart":
                chartType = LineChartDataType.MOISTURE;
                break;
            case "humLineChart":
                chartType = LineChartDataType.HUMIDITY;
                break;
            case "lightLineChart":
                chartType = LineChartDataType.LIGHT;
                break;
            default:
                break;
        }
        // Checks if the chartType exists and gives the correct properties to the lineChart.
        if (chartType != null) {
            lineChart.setTitle(chartType.getYAxisLabel() + " Chart");
            lineChart.getYAxis().setLabel(chartType.getYAxisLabel());
        }
    }

    //Updates the lineChart with sensorData based on the chartType.
    public void updateChart(SensorData data, LineChartDataType chartType) {
        XYChart.Series<String, Number> series;
        //Determines which lineChart should receive the sensorData.
        switch (chartType) {
            case TEMPERATURE:
                series = tempSeries;
                break;
            case MOISTURE:
                series = moistSeries;
                break;
            case HUMIDITY:
                series = humSeries;
                break;
            case LIGHT:
                series = lightSeries;
                break;
            default:
                throw new IllegalArgumentException("Invalid LineChartDataType: " + chartType);
        }
        if (series != null) {
            // The time at which the data point was captured
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            series.getData().add(new XYChart.Data<>(time, chartType.getSensorValue(data)));
            if (series.getData().size() > 10) {
                series.getData().remove(0);
            }
        }
    }
}