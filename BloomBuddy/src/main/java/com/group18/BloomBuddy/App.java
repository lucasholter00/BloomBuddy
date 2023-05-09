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
            SensorSettings sensorSettings = new SensorSettings(10,20, 10,20,10,20,10,20);
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
            MQTTHandler mqttHandler = new MQTTHandler(mqttCallback);

                while (true) {
                    sceneSwitcher.updateSensorData(data.getData(),LineChartDataType.MOISTURE);
                    sceneSwitcher.updateSensorData(data.getData(),LineChartDataType.TEMPERATURE);
                    sceneSwitcher.updateSensorData(data.getData(),LineChartDataType.HUMIDITY);
                    sceneSwitcher.updateSensorData(data.getData(),LineChartDataType.LIGHT);
                    newThresholdValues = sensorSettings.checkSensorReadings(data.getData());
                    for(int i= 0; i < 4; i++){
                        if(previousThresholdValues.get(i) != newThresholdValues.get(i)){
                            if(i == 0) {
                                if(newThresholdValues.get(i) == TRUE) {
                                    mqttHandler.publish("thresholdColorTemperature", "TFT_RED" );
                                }else{
                                    mqttHandler.publish("thresholdColorTemperature", "TFT_GREEN" );
                                }
                            } else if (i == 1) {
                                if(newThresholdValues.get(i) == TRUE) {
                                    mqttHandler.publish("thresholdColorMoisture", "TFT_RED" );
                                }else{
                                    mqttHandler.publish("thresholdColorMoisture", "TFT_GREEN" );
                                }
                            } else if (i == 2) {
                                if(newThresholdValues.get(i) == TRUE) {
                                    mqttHandler.publish("thresholdColorLight", "TFT_RED" );
                                }else{
                                    mqttHandler.publish("thresholdColorLight", "TFT_GREEN" );
                                }
                            } else{
                                if(newThresholdValues.get(i) == TRUE) {
                                    mqttHandler.publish("thresholdColorHumidity", "TFT_RED" );
                                }else{
                                    mqttHandler.publish("thresholdColorHumidity", "TFT_GREEN" );
                                }
                            }
                            previousThresholdValues = newThresholdValues;
                        }
                    }
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
