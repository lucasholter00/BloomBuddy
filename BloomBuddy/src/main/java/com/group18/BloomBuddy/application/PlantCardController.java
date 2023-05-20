package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.Mediator;
import com.group18.BloomBuddy.Profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlantCardController extends SceneSwitcher { //unsure
    private Profile profile;

    @FXML
    private Label humLabel;

    @FXML
    private ImageView image;


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


    private ToggleGroup toggleGroup;

    @FXML
    private Label plantName;

    @FXML
    private Label tempLabel;

    private static final List<String> imageList = new ArrayList<>();



    @FXML
    public void initialize() {
        imageList.add("cabbage.png");
        imageList.add("broccoli.png");
        imageList.add("fire flower.png");


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
                        toggle.setSelected(false);
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


    public void setData(Profile profile) throws MqttException {
        // Populate the UI with data from the profile
        image.setFitWidth(132); // Set the desired width
        image.setFitHeight(115); // Set the desired height
        this.profile = profile;

        //Change the values of setText when Currentuser is not null
        plantName.setText(profile.getName());
        lastWatered.setText(String.valueOf(profile.getLastWatered()));
        humLabel.setText(tresholdToString(profile.getHumidityLowerBound(),profile.getHumidityUpperBound()));
        if (profile.getLightLowerBound()==0){
            lightLabel.setText("Low");
        }else {
            lightLabel.setText("High");

        }
        tempLabel.setText(tresholdToString(profile.getTemperatureLowerBound(), profile.getTemperatureUpperBound()));
        moistLabel.setText(tresholdToString(profile.getMoistureLowerBound(),profile.getMoistureUpperBound()));

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
}
