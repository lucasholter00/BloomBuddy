package com.group18.BloomBuddy.application;

import java.io.IOException;

import com.group18.BloomBuddy.Mediator;
import com.group18.BloomBuddy.Profile;
import com.group18.BloomBuddy.SensorData;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class PlantCardController extends SceneSwitcher { //unsure
    private Profile profile;

    @FXML
    private Label humLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView image;

    @FXML
    private Button editPlant;


    @FXML
    private Label lightLabel;
    @FXML
    private Label moistLabel;
    @FXML
    private VBox outer;
    @FXML
    private Label lastWatered;

    @FXML
    private ToggleButton toggleButton;

    @FXML
    private RadioButton radioButton;

    private ToggleGroup toggleGroup;

    @FXML
    private Label plantName;

    @FXML
    private Label tempLabel;

    @FXML
    public void initialize() {

    }


    public void setData(Profile profile) {
        SensorData sensorData = new SensorData();
        this.profile = profile;

        //Change the values of setText when Currentuser is not null
        plantName.setText("profile.getName()");
        lastWatered.setText(String.valueOf("profile.getLastWatered())"));
        humLabel.setText(String.valueOf("sensorData.getHumidity())"));
        lightLabel.setText(String.valueOf("sensorData.getLightIntensity())"));
        tempLabel.setText(String.valueOf("sensorData.getTemperature())"));
        moistLabel.setText(String.valueOf("sensorData.getMoistureLevel())"));

        // Load and set the image
        Image plantPic = new Image("cabbage.png");
        image.setImage(plantPic);

        outer.setSpacing(30);
        VBox.setMargin(humLabel, new Insets(0, 0, 0, 10));
        VBox.setMargin(lightLabel, new Insets(0, 0, 0, 10));
        VBox.setMargin(tempLabel, new Insets(0, 0, 0, 10));
        VBox.setMargin(moistLabel, new Insets(0, 0, 0, 10));

    }

    @FXML
    public void passProfile(ActionEvent event){
       Mediator.getInstance().setEditProfile(profile);
    }

    @FXML
    public void handleEvent(ActionEvent event) throws IOException {
        passProfile(event);
        setPlantAddingScene(event);
    }
}
