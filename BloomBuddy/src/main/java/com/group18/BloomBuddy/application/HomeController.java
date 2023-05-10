package com.group18.BloomBuddy.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class HomeController extends SceneSwitcher {

    public Button statistics;

    public void show (Stage stage) throws IOException {
        URL fxmlResource = getClass().getResource("/homeScene.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlResource);
        Parent root = loader.load();
        Scene homeScene = new Scene(root,800,600);
        stage.setScene(homeScene);
        loader.getController();

        stage.setTitle("BloomBuddy");
        stage.setScene(homeScene);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.show();
    }
    public void stats() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/statScene.fxml")));
        Stage window = (Stage) statistics.getScene().getWindow();
        window.setScene(new Scene(root, 800, 600));
    }
}



