package com.group18.BloomBuddy.application;

import java.io.IOException;

import com.group18.BloomBuddy.Mediator;
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
import org.eclipse.paho.client.mqttv3.MqttException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

    @FXML
    private Button editPlantButton;


    private ToggleGroup toggleGroup;

    @FXML
    private Label plantName;

    @FXML
    private Label tempLabel;

    private static List<String> imageList = new ArrayList<>();



    @FXML
    public void initialize() {
        imageList.add("cabbage.png");
        imageList.add("broccoli.png");
        imageList.add("fire flower.png");


        //TODO make togglebutton work
        toggleGroup = new ToggleGroup();
        toggleButton.setToggleGroup(toggleGroup);

        // Add an event listener to handle selection events
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                // No ToggleButton is selected
                toggleButton.setSelected(false);
            } else {
                // Only allow one ToggleButton to be selected
                toggleGroup.getToggles().forEach(toggle -> {
                    if (toggle != newValue) {
                        ((ToggleButton) toggle).setSelected(false);
                    }
                });
            }
        });
    }

    private String randomizeImage(){
        int lastIndex = imageList.size() - 1;

        Random random = new Random();
        int randomNumber = random.nextInt(lastIndex + 1);

       return imageList.get(randomNumber);

    }
    private String formatter(LocalDateTime localDateTime){
        String formattedDateTime = "";

        if (localDateTime!=null){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
            formattedDateTime = localDateTime.format(dateTimeFormatter);
        }else {
            formattedDateTime = "Not watered";
        }
        return formattedDateTime;
    }


    public void setData(Profile profile) throws MqttException {

        image.setFitWidth(132); // Set the desired width
        image.setFitHeight(115); // Set the desired height
        SensorData sensorData = new SensorData();
        this.profile = profile;
        // Populate the UI with data from the profile

        //Change the values of setText when Currentuser is not null
        //These values need to be edited to display the correct values, or the FXML need to be edited
        plantName.setText(profile.getName());

        String lastWaterdString = formatter(profile.getLastWatered());

        lastWatered.setText(lastWaterdString);


        humLabel.setText(tresholdToString(profile.getHumidityLowerBound(),profile.getHumidityUpperBound()));
        //humLabel.setText(String.valueOf(sensorData.getHumidity()));
        if (profile.getLightLowerBound()==0){
            lightLabel.setText("Low");
        }else {
            lightLabel.setText("High");

        }
        //lightLabel.setText(String.valueOf(sensorData.getLightIntensity()));
        tempLabel.setText(tresholdToString(profile.getTemperatureLowerBound(), profile.getTemperatureUpperBound()));
        //tempLabel.setText(String.valueOf(sensorData.getTemperature()));
        moistLabel.setText(tresholdToString(profile.getMoistureLowerBound(),profile.getMoistureUpperBound()));
        //moistLabel.setText(String.valueOf(sensorData.getMoistureLevel()));

        toggleButton.setSelected(Mediator.getInstance().getCurrentUser().isActive(profile));

        // Load and set the image
        final Image plantPic = new Image(randomizeImage());
        image.setImage(plantPic);

        image.setPreserveRatio(true);
        image.setFitWidth(image.getBoundsInLocal().getWidth());
        image.setFitHeight(image.getBoundsInLocal().getHeight());
        image.setPreserveRatio(true);
        image.setFitWidth(image.getBoundsInLocal().getWidth());
        image.setFitHeight(image.getBoundsInLocal().getHeight());

        // Customize the spacing and margins
        outer.setSpacing(30);
        VBox.setMargin(humLabel, new Insets(0, 0, 0, 10));
        VBox.setMargin(lightLabel, new Insets(0, 0, 0, 10));
        VBox.setMargin(tempLabel, new Insets(0, 0, 0, 10));
        VBox.setMargin(moistLabel, new Insets(0, 0, 0, 10));

    }

    private String tresholdToString(float low, float high){
        return low+" - "+high;
    }

    @FXML
    private void activateProfile(ActionEvent actionEvent) throws IOException {
        Mediator.getInstance().getCurrentUser().setCurrentProfile(profile);
        setPlantOverviewScene(actionEvent);
    }
    @FXML
    public void passProfile(){
       Mediator.getInstance().setEditProfile(profile);
    }

    @FXML
    public void handleEvent(ActionEvent event) throws IOException {
        passProfile();
        setPlantEditingScene(event);
    }
    @FXML
    public void handleWaterEvent() throws MqttException {
        profile.setLastWatered(LocalDateTime.now());
        lastWatered.setText(formatter(profile.getLastWatered()));

    }
}
