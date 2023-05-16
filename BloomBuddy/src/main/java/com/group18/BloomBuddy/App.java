package com.group18.BloomBuddy;

import com.group18.BloomBuddy.application.LineChartDataType;
import com.group18.BloomBuddy.application.SceneSwitcher;
import javafx.application.Application;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.List;

import static java.lang.Boolean.TRUE;

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
                MQTTHandler mqttHandler = new MQTTHandler(mqttCallback, "BloomBuddyThreshold");
                for(int x = 0; x < 4; x++) {
                    checkThresholdValues(previousThresholdValues, mqttHandler, x);
                }

                while (true) {
                    sceneSwitcher.updateSensorData(data.getData(),LineChartDataType.MOISTURE);
                    sceneSwitcher.updateSensorData(data.getData(),LineChartDataType.TEMPERATURE);
                    sceneSwitcher.updateSensorData(data.getData(),LineChartDataType.HUMIDITY);
                    sceneSwitcher.updateSensorData(data.getData(),LineChartDataType.LIGHT);
                    newThresholdValues = sensorSettings.checkSensorReadings(data.getData());
                    Thread.sleep(1000);

                    for(int i = 0; i < 4; i++){ // checks if sensor values have changed beyond thresholds
                        if(previousThresholdValues.get(i) != newThresholdValues.get(i)){
                            checkThresholdValues(newThresholdValues, mqttHandler,i);
                        }
                    }
                    previousThresholdValues = newThresholdValues;

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
            }else{
                mqttHandler.publish("BloomBuddy/Threshold/Color/Moisture", "green" );
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

}


