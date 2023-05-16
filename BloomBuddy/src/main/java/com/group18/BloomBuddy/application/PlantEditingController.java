package com.group18.BloomBuddy.application;

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

public class PlantEditingController extends SceneSwitcher {
    public RadioButton LightLow;
    public RadioButton LightHigh;
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
    private void handleSaveSettingsButton(ActionEvent event) {
        saveSettings(event, new Profile(new SensorSettings(10, 20, 10, 20, 10,20,10,20)));
    }
    @FXML
    private void saveSettings(ActionEvent event, Profile profile) {

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
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Settings were successfully saved.");
                    alert.showAndWait();

                    System.out.println("New settings saved to profile:");
                    System.out.println("Temperature Lower Bound: " + settings.getTemperatureLowerBound());
                    System.out.println("Temperature Upper Bound: " + settings.getTemperatureUpperBound());
                    System.out.println("Humidity Lower Bound: " + settings.getHumidityLowerBound());
                    System.out.println("Humidity Upper Bound: " + settings.getHumidityUpperBound());
                    System.out.println("Moisture Lower Bound: " + settings.getMoistureLowerBound());
                    System.out.println("Moisture Upper Bound: " + settings.getMoistureUpperBound());
                    System.out.println("Light Lower Bound: " + settings.getLightLowerBound());
                    System.out.println("Light Upper Bound: " + settings.getLightUpperBound());
                }


            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Please enter valid numbers.");
                alert.showAndWait();
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Temperature lower bound must be less than upper bound.");
            alert.showAndWait();
            valid = true;

        }
        if (humidityLowerBound >= humidityUpperBound) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Humidity lower bound must be less than upper bound.");
            alert.showAndWait();
            valid = false;
        }
        if (moistureLowerBound >= moistureUpperBound) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("moisture lower bound must be less than upper bound.");
            alert.showAndWait();
            valid = false;

        }
        return valid;
    }

}
