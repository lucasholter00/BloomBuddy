package com.group18.BloomBuddy.application;

import javafx.scene.Node;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class SceneSwitcher {

    public void setLoginScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new LoginController().show(stage);
    }

    public void setHomeScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new HomeController().show(stage);
    }

    public void setStatScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        new StatsController().show(stage);
    }
}