package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.Mediator;
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
    private Profile profile;
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

    @FXML
    public void initialize() {
        this.profile = Mediator.getInstance().getEditProfile();
        System.out.println("Hej");
        System.out.println(profile);
    }

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

    //This method is called when the 'Save Settings' button is clicked.
    @FXML
    private void handleSaveSettingsButton(ActionEvent event) throws Exception {
     //TODO Specify what profile to edit. It should be an already existing profile!
        System.out.println(profile);
        saveSettings(event, profile);
    }

    //This method is responsible for saving the sensor settings for a given profile.
    //It first validates the input bounds, then tries to update the sensor settings of the profile.
    @FXML
    private void saveSettings(ActionEvent event, Profile profile) {
        editingLabel.setWrapText(true);
            try {
                if(validateBounds()) {
                    SensorSettings settings = profile.getSensorSettings();
                    settings.setTemperatureLowerBound(Float.parseFloat(TempLowBound.getText()));
                    settings.setTemperatureUpperBound(Float.parseFloat(TempUpBound.getText()));
                    settings.setHumidityLowerBound(Float.parseFloat(HumLowBound.getText()));
                    settings.setHumidityUpperBound(Float.parseFloat(HumUpBound.getText()));
                    settings.setMoistureLowerBound(Float.parseFloat(MoistLowBound.getText()));
                    settings.setMoistureUpperBound(Float.parseFloat(MoistUpBound.getText()));
                    if (LightLow.isSelected()) {
                        settings.setLightLowerBound(0);
                        settings.setLightUpperBound(512);
                    } else if (LightHigh.isSelected()) {
                        settings.setLightLowerBound(512);
                        settings.setLightUpperBound(2000);
                    }
                    editingLabel.setText("Settings were successfully saved.");
                    System.out.println("Temperature lower bound: " + profile.getSensorSettings().getTemperatureLowerBound());
                    System.out.println("Temperature upper bound: " + profile.getSensorSettings().getTemperatureUpperBound());
                }
            } catch (NumberFormatException e) {
                editingLabel.setText("Please enter valid numbers.");
            }
            
        }

    //This method checks if the sensor settings bounds are valid.
    //Specifically, it checks if the lower bounds are less than the upper bounds for the sensor settings.
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
