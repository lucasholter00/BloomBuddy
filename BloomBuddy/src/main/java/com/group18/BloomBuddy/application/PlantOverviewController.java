package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.CurrentUser;
<<<<<<<<< Temporary merge branch 1
import com.group18.BloomBuddy.Mediator;
import javafx.event.ActionEvent;
=========
import com.group18.BloomBuddy.DataBaseConnection;
import com.group18.BloomBuddy.Mediator;
import com.group18.BloomBuddy.Profile;
import javafx.fxml.FXML;
>>>>>>>>> Temporary merge branch 2
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
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
    @FXML
    private AnchorPane anchor;
    @FXML
    private Button refreshPlant;


    private DataBaseConnection dbConn = new DataBaseConnection();
    private CurrentUser currentUser;


    //private List<Profile> profiles = currentUser.getProfiles();
    @FXML
    public void initialize() {
        currentUser = Mediator.getInstance().getCurrentUser();
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
@FXML
    private void regenerateProfiles(){
    System.out.println("ja det är rätt");
        generateProfiles(currentUser.getProfiles());
    }


    private void generateProfiles(List<Profile> profileList) {
        int columns = 0;
        int rows = 1;

        try {
            FXMLLoader fxmlLoader;
            for (int i = 0; i < profileList.size(); i++) {
                fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/plantCard.fxml"));

                VBox profileBox = fxmlLoader.load();
                PlantCardController plantCardController = fxmlLoader.getController();
                plantCardController.setData(profileList.get(i));

                if (columns == 2) {
                    columns = 0;
                    rows++;
                }

                gridPane.add(profileBox, columns++, rows++);
                GridPane.setMargin(profileBox, new Insets(10));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}








