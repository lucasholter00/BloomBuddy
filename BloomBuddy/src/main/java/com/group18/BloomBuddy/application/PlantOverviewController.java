package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.CurrentUser;
import com.group18.BloomBuddy.Mediator;
import com.group18.BloomBuddy.Profile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class PlantOverviewController extends SceneSwitcher {


    @FXML
    private GridPane gridPane;


    //private List<Profile> profiles = currentUser.getProfiles();
    @FXML
    public void initialize() {
        CurrentUser currentUser = Mediator.getInstance().getCurrentUser();
        List<Profile> profiles = currentUser.getProfiles();
        generateProfiles(profiles);


    }


    public void show(Stage stage) throws IOException {


        URL fxmlResource = getClass().getResource("/plantOverviewScene.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlResource);
        Parent root = loader.load();
        Scene statScene = new Scene(root, 800, 600);
        stage.setScene(statScene);

        stage.setTitle("BloomBuddy");
        stage.setScene(statScene);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.show();

    }

    private void generateProfiles(List<Profile> profileList) {
        int columns = 0;
        int rows = 1;

        try {
            FXMLLoader fxmlLoader;
            for (Profile profile : profileList) {
                fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/plantCard.fxml"));

                VBox profileBox = fxmlLoader.load();
                PlantCardController plantCardController = fxmlLoader.getController();
                plantCardController.setData(profile);

                if (columns == 2) {
                    columns = 0;
                    rows++;
                }

                gridPane.add(profileBox, columns++, rows++);
                GridPane.setMargin(profileBox, new Insets(10));
            }
        } catch (IOException | MqttException e) {
            throw new RuntimeException(e);
        }
    }

}








