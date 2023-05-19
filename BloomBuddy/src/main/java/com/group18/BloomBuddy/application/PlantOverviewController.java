package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.CurrentUser;
import com.group18.BloomBuddy.Mediator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
public class PlantOverviewController extends SceneSwitcher {
    public void show(Stage stage) throws IOException {
        URL fxmlResource = getClass().getResource("/plantOverviewScene.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlResource);
        Parent root = loader.load();
        Scene statScene = new Scene(root, 800, 600);
        stage.setScene(statScene);
        loader.getController();

        stage.setTitle("BloomBuddy");
        stage.setScene(statScene);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.show();
    }
}
