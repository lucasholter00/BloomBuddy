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
            SensorSettings sensorSettings = new SensorSettings(10,20, 0,20,0,2000,10,20);
            List<Boolean> previousThresholdValues = sensorSettings.checkSensorReadings(data.getData());;
            List<Boolean> newThresholdValues;
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
                
                checkThresholdValues(previousThresholdValues, mqttHandler);

                while (true) {
                    sceneSwitcher.updateSensorData(data.getData(),LineChartDataType.MOISTURE);
                    sceneSwitcher.updateSensorData(data.getData(),LineChartDataType.TEMPERATURE);
                    sceneSwitcher.updateSensorData(data.getData(),LineChartDataType.HUMIDITY);
                    sceneSwitcher.updateSensorData(data.getData(),LineChartDataType.LIGHT);
                    newThresholdValues = sensorSettings.checkSensorReadings(data.getData());
                    Thread.sleep(1000);
                    if(previousThresholdValues != newThresholdValues){
                        checkThresholdValues(newThresholdValues, mqttHandler);
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

    public void checkThresholdValues(List<Boolean> thresholdValues, MQTTHandler mqttHandler) throws MqttException{
        for(int i= 0; i < 4; i++){
                if(i == 0) {
                    if(thresholdValues.get(i) == TRUE) {
                        mqttHandler.publish("BloomBuddy/Threshold/Color/Temperature", "red" );
                        System.out.println("Temp bad");
                    }else{
                        mqttHandler.publish("BloomBuddy/Threshold/Color/Temperature", "green" );
                        System.out.println("Temp good");
                    }
                } else if (i == 1) {
                    if(thresholdValues.get(i) == TRUE) {
                        mqttHandler.publish("BloomBuddy/Threshold/Color/Moisture", "red" );
                        System.out.println("moist bad");
                    }else{
                        mqttHandler.publish("BloomBuddy/Threshold/Color/Moisture", "green" );
                        System.out.println("moist good");
                    }
                } else if (i == 2) {
                    if(thresholdValues.get(i) == TRUE) {
                        mqttHandler.publish("BloomBuddy/Threshold/Color/Light", "red" );
                        System.out.println("light bad");
                    }else{
                        mqttHandler.publish("BloomBuddy/Threshold/Color/Light", "green" );
                        System.out.println("light good");
                    }
                } else{
                    if(thresholdValues.get(i) == TRUE) {
                        mqttHandler.publish("BloomBuddy/Threshold/Color/Humidity", "red" );
                        System.out.println("humidity bad");
                    }else{
                        mqttHandler.publish("BloomBuddy/Threshold/Color/Humidity", "green" );
                        System.out.println("humidity good");
                    }
                }
            }
    }
}


