package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.SensorData;
import com.group18.BloomBuddy.SensorInteractor;
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

public class StatsController {
    @FXML
    private LineChart<String, Number> tempLineChart;
    @FXML
    private LineChart<String, Number> moistLineChart;
    @FXML
    private LineChart<String, Number> humLineChart;
    @FXML
    private LineChart<String, Number> lightLineChart;

    @FXML
    public void initialize(){
        initializeChart(tempLineChart);
        initializeChart(moistLineChart);
        initializeChart(humLineChart);
        initializeChart(lightLineChart);
    }
    public void show (Stage stage) throws IOException {
        URL fxmlResource = getClass().getResource("/statScene.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlResource);
        Parent root = loader.load();
        Scene statScene = new Scene(root,800,600);
        stage.setScene(statScene);
        loader.getController();

        stage.setTitle("BloomBuddy");
        stage.setScene(statScene);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.show();
    }
    public void initializeChart(LineChart<String, Number> lineChart){
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
        switch(lineChart.getId()) {
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
        LineChart<String, Number> lineChart;
        //Determines which lineChart should receive the sensorData.
        switch (chartType) {
            case TEMPERATURE:
                lineChart = tempLineChart;
                break;
            case MOISTURE:
                lineChart = moistLineChart;
                break;
            case HUMIDITY:
                lineChart = humLineChart;
                break;
            case LIGHT:
                lineChart = lightLineChart;
                break;
            default:
                throw new IllegalArgumentException("Invalid LineChartDataType: " + chartType);
        }
        //Initializes a Series, if the lineChart is empty it creates a new Series, otherwise it gets the first line chart's data.
        XYChart.Series<String, Number> series = lineChart.getData().isEmpty() ? new XYChart.Series<>() : lineChart.getData().get(0);
        //The time in which the data point was captured.
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        series.getData().add(new XYChart.Data<>(time, chartType.getSensorValue(data)));
        if (series.getData().size() > 10) {
            series.getData().remove(0);
        }
        if (lineChart.getData().isEmpty()) {
            lineChart.getData().add(series);
        }
    }
}

