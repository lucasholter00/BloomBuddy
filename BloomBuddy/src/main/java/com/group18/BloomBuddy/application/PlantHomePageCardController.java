package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.Mediator;
import com.group18.BloomBuddy.Profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlantHomePageCardController extends SceneSwitcher {

    @FXML
    private ImageView image;

    private Profile profile;

    @FXML
    private ToggleButton toggleButton;

    @FXML
    private Label lastWatered;

    @FXML
    private VBox outer;

    @FXML
    private Label plantName;

    private static final List<String> imageList = new ArrayList<>();
    private ToggleGroup toggleGroup;

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

    public void setData(Profile profile) {
        image.setFitWidth(132); // Set the desired width
        image.setFitHeight(115); // Set the desired height
        this.profile = profile;
        // Populate the UI with data from the profile

        //Change the values of setText when Currentuser is not null
        //These values need to be edited to display the correct values, or the FXML need to be edited
        plantName.setText(profile.getName());
        lastWatered.setText(formatter(profile.getLastWatered()));


        toggleButton.setSelected(Mediator.getInstance().getCurrentUser().isActive(profile));

        // Load and set the image
        final Image plantPic = new Image(profile.getImageFilename());
        image.setImage(plantPic);

        image.setPreserveRatio(true);
        image.setFitWidth(image.getBoundsInLocal().getWidth());
        image.setFitHeight(image.getBoundsInLocal().getHeight());
        image.setPreserveRatio(true);
        image.setFitWidth(image.getBoundsInLocal().getWidth());
        image.setFitHeight(image.getBoundsInLocal().getHeight());

        // Customize the spacing and margins
        outer.setSpacing(30);



    }

    @FXML
    private void activateProfile(ActionEvent actionEvent) throws IOException {
        Mediator.getInstance().getCurrentUser().setCurrentProfile(profile);
        setHomeScene(actionEvent);
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
}
