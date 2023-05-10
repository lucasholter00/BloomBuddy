package com.group18.BloomBuddy.application;

import javafx.scene.Node;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class SceneSwitcher {

    public void setLoginScene(ActionEvent event) throws IOException {
        new LoginController().show((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    public void setHomeScene(ActionEvent event) throws IOException {
        new HomeController().show((Stage) ((Node) event.getSource()).getScene().getWindow());
    }
    public void setStatScene(ActionEvent event) throws IOException {
        new StatsController().show((Stage) ((Node) event.getSource()).getScene().getWindow());
    }



}