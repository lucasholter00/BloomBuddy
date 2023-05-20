package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.Mediator;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;

public class SceneSwitcher {

    public void setLoginScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new LoginController().show(stage);
        Mediator.getInstance().setCurrentUser(null);
    }

    public void setAccountCreateScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new CreateAccountController().show(stage);
    }

    public void setHomeScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new HomeController().show(stage);
    }

    public void setStatScene(ActionEvent event) throws IOException, MqttException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new StatsController().show(stage);
    }

    public void setPlantOverviewScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new PlantOverviewController().show(stage);
    }
    public void setPlantAddingScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new PlantAddingController().show(stage);
    }
    public void setPlantEditingScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new PlantEditingController().show(stage);
    }
}