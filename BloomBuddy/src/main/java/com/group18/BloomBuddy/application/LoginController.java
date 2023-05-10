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
public class LoginController extends SceneSwitcher{

    public Button loginButton;

    public void show (Stage stage) throws IOException {
        URL fxmlResource = getClass().getResource("/loginScene.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlResource);
        Parent root = loader.load();
        Scene statScene = new Scene(root,800,600);
        stage.setScene(statScene);
        loader.getController();

        stage.setTitle("BloomBuddy");
        stage.setScene(statScene);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.show();
    }

    public void login () throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/homeScene.fxml")));
        Stage window = (Stage) loginButton.getScene().getWindow();
        window.setScene(new Scene(root,800,600));
    }
}
