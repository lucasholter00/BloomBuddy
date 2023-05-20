package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.scene.chart.XYChart.Data;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static java.lang.Boolean.TRUE;

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

    private XYChart.Series<String, Number> tempLowerSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> tempUpperSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> moistLowerSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> moistUpperSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> humLowerSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> humUpperSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> lightLowerSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> lightUpperSeries = new XYChart.Series<>();
    private Profile profile;
    @FXML
    public void initialize() throws MqttException {
        profile = Mediator.getInstance().getCurrentUser().getCurrentProfile();
        initializeChart(tempLineChart);
        initializeChart(moistLineChart);
        initializeChart(humLineChart);
        initializeChart(lightLineChart);
        startSensorThread();
        initializeSeries();
    }

    public void show(Stage stage) throws IOException, MqttException {
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
                SensorSettings sensorSettings = new SensorSettings(10,20, 0,100,500,2000,10,20);
                List<Boolean> previousThresholdValues = sensorSettings.checkSensorReadings(data.getData());//is used to check if sensor values have changed beyond the threshold values
                List<Boolean> newThresholdValues; //is used to check if sensor values have changed beyond the threshold values
                MqttCallback mqttCallback = new MqttCallback() {
                    public void connectionLost(Throwable cause) {
                        System.out.println("Connection lost: " + cause.getMessage());
                    }

                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        //empty for now
                    }

                    public void deliveryComplete(IMqttDeliveryToken token) {
                        // not used in this example
                    }
                };
                MQTTHandler mqttHandler = new MQTTHandler(mqttCallback);
                for(int x = 0; x < 4; x++) {
                    checkThresholdValues(previousThresholdValues, mqttHandler, x);
                }
                while (true) {
                    Thread.sleep(1000);
                    System.out.println(data.getData());
                    Platform.runLater(() -> {
                        try {
                            updateChart(data.getData(), LineChartDataType.LIGHT);
                            updateChart(data.getData(), LineChartDataType.HUMIDITY);
                            updateChart(data.getData(), LineChartDataType.TEMPERATURE);
                            updateChart(data.getData(), LineChartDataType.MOISTURE);
                        } catch (MqttException e) {
                            throw new RuntimeException(e);

                        }
                    });
                    newThresholdValues = sensorSettings.checkSensorReadings(data.getData());
                    System.out.println(data.getData());



                    for(int i = 0; i < 4; i++){ // checks if sensor values have changed beyond thresholds
                        if(previousThresholdValues.get(i) != newThresholdValues.get(i)){
                            checkThresholdValues(newThresholdValues, mqttHandler,i);
                        }
                    }
                    previousThresholdValues = newThresholdValues;
                }
            } catch (InterruptedException | MqttException e) {
                e.printStackTrace();
            }
        });
        sensorThread.setDaemon(true);
        sensorThread.start();
    }
    public void checkThresholdValues(List<Boolean> thresholdValues, MQTTHandler mqttHandler, int sensor) throws MqttException{ // only sens messages for one sensor at a time specified by int sensor
        if(sensor == 0) {
            if(thresholdValues.get(sensor) == TRUE) {
                mqttHandler.publish("BloomBuddy/Threshold/Color/Temperature", "red" );
            }else{
                mqttHandler.publish("BloomBuddy/Threshold/Color/Temperature", "green" );
            }
        } else if (sensor == 1) {
            if(thresholdValues.get(sensor) == TRUE) {
                mqttHandler.publish("BloomBuddy/Threshold/Color/Moisture", "red" );
                System.out.println("red");
            }else{
                mqttHandler.publish("BloomBuddy/Threshold/Color/Moisture", "green" );
                System.out.println("green");

            }
        } else if (sensor == 2) {
            if(thresholdValues.get(sensor) == TRUE) {
                mqttHandler.publish("BloomBuddy/Threshold/Color/Light", "red" );
            }else{
                mqttHandler.publish("BloomBuddy/Threshold/Color/Light", "green" );
            }
        } else{
            if(thresholdValues.get(sensor) == TRUE) {
                mqttHandler.publish("BloomBuddy/Threshold/Color/Humidity", "red" );
            }else{
                mqttHandler.publish("BloomBuddy/Threshold/Color/Humidity", "green" );
            }
        }
    }
    public void initializeSeries() {
        tempLineChart.getData().add(tempSeries);
        moistLineChart.getData().add(moistSeries);
        humLineChart.getData().add(humSeries);
        lightLineChart.getData().add(lightSeries);
        tempLineChart.getData().addAll(tempLowerSeries, tempUpperSeries);
        moistLineChart.getData().addAll(moistLowerSeries, moistUpperSeries);
        humLineChart.getData().addAll(humLowerSeries, humUpperSeries);
        lightLineChart.getData().addAll(lightLowerSeries, lightUpperSeries);
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
    public void updateChart(SensorData data, LineChartDataType chartType) throws MqttException {
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
        updateThresholdSeries();
    }
    public void updateThresholdSeries() throws MqttException {
        // Get current time
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        // Update lower and upper threshold series for each sensor
        updateSeriesWithThreshold(time, tempLowerSeries, profile.getTemperatureLowerBound());
        updateSeriesWithThreshold(time, tempUpperSeries, profile.getTemperatureUpperBound());
        updateSeriesWithThreshold(time, moistLowerSeries, profile.getMoistureLowerBound());
        updateSeriesWithThreshold(time, moistUpperSeries, profile.getMoistureUpperBound());
        updateSeriesWithThreshold(time, humLowerSeries, profile.getHumidityLowerBound());
        updateSeriesWithThreshold(time, humUpperSeries, profile.getHumidityUpperBound());
        updateSeriesWithThreshold(time, lightLowerSeries, profile.getLightLowerBound());
        updateSeriesWithThreshold(time, lightUpperSeries, profile.getLightUpperBound());

    }

    private void updateSeriesWithThreshold(String time, XYChart.Series<String, Number> series, float threshold) {
            // If series has more than 36 data points, remove the oldest one
            if (series.getData().size() > 36) {
                series.getData().remove(0);
            }
        series.getData().add(new Data<>(time, threshold));
        series.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #000000;");


    }
}