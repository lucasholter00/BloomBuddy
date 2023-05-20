package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.CurrentUser;
import javafx.event.ActionEvent;

import com.group18.BloomBuddy.Mediator;
import com.group18.BloomBuddy.Profile;
import com.group18.BloomBuddy.SensorData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class PlantCardController extends SceneSwitcher { //unsure
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

    private Profile lastEdited;

    @FXML
    private RadioButton radioButton;

    @FXML
    private Button editPlantButton;


    private ToggleGroup toggleGroup;

    @FXML
    private Label plantName;

    @FXML
    private Label tempLabel;

    private Profile profile;


    public void setProfile(Profile profile) {
        this.profile = profile;
    }



    @FXML
    public void initialize() {

        //TODO make togglebutton work
        toggleGroup = new ToggleGroup();
        toggleButton.setToggleGroup(toggleGroup);

        // Add an event listener to handle selection events
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                // No ToggleButton is selected
                toggleButton.setSelected(false);
            }
        });
    }

    public void setData(Profile profile) {
        // Populate the UI with data from the profile

        // Set the plant name, last watered, and sensor data
        plantName.setText(profile.getName());
        lastWatered.setText(String.valueOf(profile.getLastWatered()));
        humLabel.setText(String.valueOf("profile.getHumidity()"));
        lightLabel.setText(String.valueOf("profile.getLightIntensity()"));
        tempLabel.setText(String.valueOf("profile.getTemperature()"));
        moistLabel.setText(String.valueOf("profile.getMoistureLevel()"));

        // Load and set the image
        Image plantPic = new Image("cabbage.png");
        image.setImage(plantPic);

        // Customize the spacing and margins
        outer.setSpacing(30);
        VBox.setMargin(humLabel, new Insets(0, 0, 0, 10));
        VBox.setMargin(lightLabel, new Insets(0, 0, 0, 10));
        VBox.setMargin(tempLabel, new Insets(0, 0, 0, 10));
        VBox.setMargin(moistLabel, new Insets(0, 0, 0, 10));

        // Set the active state of the ToggleButton
        toggleButton.setSelected(Mediator.getInstance().getCurrentUser().isActive(profile));    }

}
