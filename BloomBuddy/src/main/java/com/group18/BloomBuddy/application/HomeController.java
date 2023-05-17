package com.group18.BloomBuddy.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;

public class HomeController extends SceneSwitcher {

    public Button statisticsButton;
    public Label recommendationText;
    public Label seasonText;

    public void show (Stage stage) throws IOException {
        URL fxmlResource = getClass().getResource("/homeScene.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlResource);


        Parent root = loader.load();
        Scene homeScene = new Scene(root, 800, 600);
        stage.setScene(homeScene);

        stage.setTitle("BloomBuddy");
        stage.setScene(homeScene);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.show();
    }


    public void changeRecommendationText(javafx.event.ActionEvent actionEvent) {
        seasonText.setText("Summer");
        recommendationText.setText("funkakakakaka");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime springStart = LocalDateTime.of(now.getYear(), Month.MARCH, 1, 0, 0);
        LocalDateTime summerStart = LocalDateTime.of(now.getYear(), Month.JUNE, 1, 0, 0);
        LocalDateTime autumnStart = LocalDateTime.of(now.getYear(), Month.SEPTEMBER, 1, 0, 0);
        LocalDateTime winterStart = LocalDateTime.of(now.getYear(), Month.NOVEMBER, 1, 0, 0);
        if (now.isAfter(springStart) && now.isBefore(summerStart)) { //Spring
            seasonText.setText("Spring");

            recommendationText.setText("Grow a vitsippa");
        } else if(now.isAfter(summerStart) && now.isBefore(autumnStart)) {
            seasonText.setText("Summer");
            recommendationText.setText("Grow a jordgubbe");
        } else if(now.isAfter(autumnStart) && now.isBefore(winterStart)) {
            seasonText.setText("Autumn");
            recommendationText.setText("Grow a pumpa");
        } else {
            seasonText.setText("Winter");
            recommendationText.setText("Grow a istapp");
        }

    }
}



