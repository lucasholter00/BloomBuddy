package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.DataBaseConnection;
import com.group18.BloomBuddy.Profile;
import com.group18.BloomBuddy.SensorSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PlantEditingController extends SceneSwitcher {
    public RadioButton LightLow;
    public RadioButton LightHigh;
    public Label editingLabel;
    @FXML
    private TextField TempLowBound;
    @FXML
    private TextField TempUpBound;
    @FXML
    private TextField HumLowBound;
    @FXML
    private TextField HumUpBound;
    @FXML
    private TextField MoistLowBound;
    @FXML
    private TextField MoistUpBound;
    @FXML
    private Button saveSettingsButton;

    public void show(Stage stage) throws IOException {
        URL fxmlResource = getClass().getResource("/plantEditingScene.fxml");
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
    @FXML
    private void handleSaveSettingsButton(ActionEvent event) throws Exception {
        saveSettings(event, new Profile(new SensorSettings(10, 20, 10, 20, 10,20,10,20), "Felix"));
    }
    @FXML
    private void saveSettings(ActionEvent event, Profile profile) throws MqttException {
            DataBaseConnection db = new DataBaseConnection();
            db.addProfile(profile, "Felix");

            try {
                if(validateBounds()) {
                    SensorSettings settings = profile.getSensorSettings();
                    profile.setTemperatureLowerBound(Float.parseFloat(TempLowBound.getText()));
                    profile.setTemperatureUpperBound(Float.parseFloat(TempUpBound.getText()));
                    profile.setHumidityLowerBound(Float.parseFloat(HumLowBound.getText()));
                    profile.setHumidityUpperBound(Float.parseFloat(HumUpBound.getText()));
                    profile.setMoistureLowerBound(Float.parseFloat(MoistLowBound.getText()));
                    profile.setMoistureUpperBound(Float.parseFloat(MoistUpBound.getText()));
                    if (LightLow.isSelected()) {
                        profile.setLightLowerBound(0);
                        profile.setLightUpperBound(512);
                    } else if (LightHigh.isSelected()) {
                        profile.setLightLowerBound(512);
                        profile.setLightUpperBound(2000);
                    }
                    editingLabel.setText("Settings were successfully saved.");
                }
            } catch (NumberFormatException e) {
                editingLabel.setText("Please enter valid numbers.");
            }
        }

    public Boolean validateBounds(){
        boolean valid = true;
        float temperatureLowerBound = Float.parseFloat(TempLowBound.getText());
        float temperatureUpperBound = Float.parseFloat(TempUpBound.getText());
        float humidityLowerBound = Float.parseFloat(HumLowBound.getText());
        float humidityUpperBound = Float.parseFloat(HumUpBound.getText());
        float moistureLowerBound = Float.parseFloat(MoistLowBound.getText());
        float moistureUpperBound = Float.parseFloat(MoistUpBound.getText());
        if (temperatureLowerBound >= temperatureUpperBound) {
           editingLabel.setText("Temperature lower bound must be less than upper bound.");
           valid = false;
        }
        if (humidityLowerBound >= humidityUpperBound) {
            editingLabel.setText("Humidity lower bound must be less than upper bound.");
            valid = false;
        }
        if (moistureLowerBound >= moistureUpperBound) {
            editingLabel.setText("moisture lower bound must be less than upper bound.");
            valid = false;
        }
        return valid;
    }
}
