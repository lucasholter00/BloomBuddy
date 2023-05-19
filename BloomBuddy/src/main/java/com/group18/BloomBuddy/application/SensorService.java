package com.group18.BloomBuddy.application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.group18.BloomBuddy.*;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;

public class SensorService extends Service<Void>{

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {

            @Override
            protected Void call() throws Exception {
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
                        try{
                            Thread.sleep(1000);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                        Platform.runLater(() -> {
                           // updateChart(data.getData(), LineChartDataType.LIGHT);
                           // updateChart(data.getData(), LineChartDataType.HUMIDITY);
                           // updateChart(data.getData(), LineChartDataType.TEMPERATURE);
                           // updateChart(data.getData(), LineChartDataType.MOISTURE);

                        });
                        newThresholdValues = sensorSettings.checkSensorReadings(data.getData());
                        System.out.println(data.getData() + "Stat");



                        for(int i = 0; i < 4; i++){ // checks if sensor values have changed beyond thresholds
                            if(previousThresholdValues.get(i) != newThresholdValues.get(i)){
                                checkThresholdValues(newThresholdValues, mqttHandler,i);
                            }
                        }
                        previousThresholdValues = newThresholdValues;
                        if(isCancelled()){
                            break;
                        }
                    }
                } catch (MqttException e) {
                e.printStackTrace();
                }
                return null;

            }
        };
    }
    
    public void checkThresholdValues(List<Boolean> thresholdValues, MQTTHandler mqttHandler, int sensor) throws MqttException{ // only sens messages for one sensor at a time specified by int sensor
        if(sensor == 0) {
            if(thresholdValues.get(sensor) == true) {
                mqttHandler.publish("BloomBuddy/Threshold/Color/Temperature", "red" );
            }else{
                mqttHandler.publish("BloomBuddy/Threshold/Color/Temperature", "green" );
            }
        } else if (sensor == 1) {
            if(thresholdValues.get(sensor) == true) {
                mqttHandler.publish("BloomBuddy/Threshold/Color/Moisture", "red" );
                System.out.println("red");
            }else{
                mqttHandler.publish("BloomBuddy/Threshold/Color/Moisture", "green" );
                System.out.println("green");

            }
        } else if (sensor == 2) {
            if(thresholdValues.get(sensor) == true) {
                mqttHandler.publish("BloomBuddy/Threshold/Color/Light", "red" );
            }else{
                mqttHandler.publish("BloomBuddy/Threshold/Color/Light", "green" );
            }
        } else{
            if(thresholdValues.get(sensor) == true) {
                mqttHandler.publish("BloomBuddy/Threshold/Color/Humidity", "red" );
            }else{
                mqttHandler.publish("BloomBuddy/Threshold/Color/Humidity", "green" );
            }
        }
    }

/*    public void updateChart(SensorData data, LineChartDataType chartType) {
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
    }*/


}
