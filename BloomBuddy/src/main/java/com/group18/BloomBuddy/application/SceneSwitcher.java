package com.group18.BloomBuddy.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class SceneSwitcher {

private final Stage stage;

    public SceneSwitcher(Stage stage) {
        this.stage = stage;
    }

    public void setLoginScene() throws IOException {
        URL fxmlResource = getClass().getResource("/statScene.fxml");
        FXMLLoader loader1 = new FXMLLoader();
        loader1.setLocation(fxmlResource);
        Parent root1 = loader1.load();
        Scene loginScene = new Scene(root1);

        stage.setTitle("BloomBuddy");
        stage.setScene(loginScene);
        stage.setResizable(false);
        stage.setFullScreen(true);
        stage.show();
    }
}