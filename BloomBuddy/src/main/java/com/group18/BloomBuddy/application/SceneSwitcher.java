package com.group18.BloomBuddy.application;

import javafx.scene.Node;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;

public class SceneSwitcher {

    public void setLoginScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new LoginController().show(stage);
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
    public void setEditingAddingScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new PlantEditingController().show(stage);
    }
}