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
    private Profile saveSettings(ActionEvent event) {
        SensorSettings test = new SensorSettings(10, 20, 10, 20, 10,20, 10,20);
        Profile newProfile = new Profile(test);
        try {
            float temperatureLowerBound = Float.parseFloat(TempLowBound.getText());
            float temperatureUpperBound = Float.parseFloat(TempUpBound.getText());
            float humidityLowerBound = Float.parseFloat(HumLowBound.getText());
            float humidityUpperBound = Float.parseFloat(HumUpBound.getText());
            float moistureLowerBound = Float.parseFloat(MoistLowBound.getText());
            float moistureUpperBound = Float.parseFloat(MoistUpBound.getText());
            float lightLowerBound = 0;
            float lightUpperBound = 0;
            if(LightLow.isSelected()){
                lightLowerBound = 0;
                lightUpperBound = 512;
            } else if (LightHigh.isSelected()) {
                lightLowerBound = 512;
                lightUpperBound = 2000;
            }

            //Validate the bounds
            if (temperatureLowerBound >= temperatureUpperBound) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Temperature lower bound must be less than upper bound.");
                alert.showAndWait();
                return null;
            }
            if (humidityLowerBound >= humidityUpperBound) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Humidity lower bound must be less than upper bound.");
                alert.showAndWait();
                return null;            }
            if (moistureLowerBound >= moistureUpperBound) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("moisture lower bound must be less than upper bound.");
                alert.showAndWait();
                return null;
            }
            //if all validations pass, create the new SensorSettings object.
            SensorSettings settings = new SensorSettings(temperatureLowerBound, temperatureUpperBound, moistureLowerBound, moistureUpperBound, lightLowerBound, lightUpperBound, humidityLowerBound, humidityUpperBound);
            newProfile.setSensorSettings(settings);

        } catch (NumberFormatException e) {
            // Handle the case where a value is not a valid float or any other expected data type.
        }
        return newProfile;
    }

}
