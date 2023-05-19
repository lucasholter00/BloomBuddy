package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.CurrentUser;
import com.group18.BloomBuddy.Mediator;
import com.group18.BloomBuddy.Profile;
import com.group18.BloomBuddy.SensorSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.net.URL;

public class PlantAddingController extends SceneSwitcher {
    public TextField plantName;
    public RadioButton LightLow;
    public RadioButton LightHigh;
    public TextField TempLowBound;
    public TextField TempUpBound;
    public TextField HumLowBound;
    public TextField HumUpBound;
    public TextField MoistLowBound;
    public TextField MoistUpBound;
    public Label accountCreationLabel;

    public void show(Stage stage) throws IOException {
        URL fxmlResource = getClass().getResource("/plantAddingScene.fxml");
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
    public void buttonCreatePlantProfile() throws MqttException {
        accountCreationLabel.setWrapText(true);
        try {
            if (validateProfile()) {
                float lightLower = 0;
                float lightHigher = 0;
                if (LightLow.isSelected()) {
                    lightLower = 0;
                    lightHigher = 512;
                } else if (LightHigh.isSelected()) {
                    lightLower = 512;
                    lightHigher = 2000;
                }
                SensorSettings sensorSettings = new SensorSettings(
                        Float.parseFloat(TempLowBound.getText()),
                        Float.parseFloat(TempUpBound.getText()),
                        Float.parseFloat(MoistLowBound.getText()),
                        Float.parseFloat(MoistUpBound.getText()),
                        lightLower,
                        lightHigher,
                        Float.parseFloat(HumLowBound.getText()),
                        Float.parseFloat(HumUpBound.getText())
                );
                accountCreationLabel.setText("Profile was successfully created.");
                Profile profile = new Profile(sensorSettings, plantName.getText());
                CurrentUser currentUser = Mediator.getInstance().getCurrentUser();
                currentUser.newProfile(profile);
            }
        } catch (NumberFormatException e){
            accountCreationLabel.setText("Please enter valid numbers.");
        }
    }
    public boolean validateProfile() {
        boolean valid = true;
        if (plantName.getText().isBlank()) {
            accountCreationLabel.setText("Enter a name for your plant.");
            valid = false;
        }
        if (TempLowBound.getText().isBlank() || TempUpBound.getText().isBlank()
                || HumLowBound.getText().isBlank() || HumUpBound.getText().isBlank()
                || MoistLowBound.getText().isBlank() || MoistUpBound.getText().isBlank()) {
            accountCreationLabel.setText("One or more bounds are blank. Please enter values for all bounds.");
            valid = false;
        } else {
            float temperatureLowerBound = Float.parseFloat(TempLowBound.getText());
            float temperatureUpperBound = Float.parseFloat(TempUpBound.getText());
            float humidityLowerBound = Float.parseFloat(HumLowBound.getText());
            float humidityUpperBound = Float.parseFloat(HumUpBound.getText());
            float moistureLowerBound = Float.parseFloat(MoistLowBound.getText());
            float moistureUpperBound = Float.parseFloat(MoistUpBound.getText());

            if (temperatureLowerBound >= temperatureUpperBound) {
                accountCreationLabel.setText("Temperature lower bound must be less than upper bound.");
                valid = false;
            }
            if (humidityLowerBound >= humidityUpperBound) {
                accountCreationLabel.setText("Humidity lower bound must be less than upper bound.");
                valid = false;
            }
            if (moistureLowerBound >= moistureUpperBound) {
                accountCreationLabel.setText("Moisture lower bound must be less than upper bound.");
                valid = false;
            }
        }
        return valid;
    }
}
