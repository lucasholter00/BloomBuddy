package com.group18.BloomBuddy.application;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Random;

public class HomeController extends SceneSwitcher {

    public Button statisticsButton;
    public Label recommendationText;
    public Label seasonText;
    public ImageView recommendationImage;

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

            Random random = new Random();
            int randomNumber = random.nextInt(6);
            Image cabbage = new Image("cabbage.png");
            Image cauliflower = new Image("cauliflower.png");
            Image kale = new Image("kale.png");
            Image broccoli = new Image("broccoli.png");
            Image lettuce = new Image("lettuce.png");
            Image onions = new Image("onion.png");
            switch (randomNumber){
                case 0:
                    recommendationText.setText("Grow a cauliflower.\nCauliflower is rich in vitamin C which is good for skin,\n healing, and gum health");
                    recommendationImage.setImage(cauliflower);
                    break;
                case 1:
                    recommendationText.setText("Grow a kale.\nKale is a good source of vitamins A, C and K.\nKale also contains antioxidants,\nwhich promote general health and well-being.");
                    recommendationImage.setImage(kale);
                    break;
                case 2:
                    recommendationText.setText("Grow a broccoli.\nBroccoli is a cruciferous vegetable rich many nutrients.\nIt is a good source of vitamin C,\n vitamin K, and vitamin A. ");
                    recommendationImage.setImage(broccoli);
                    break;
                case 3:
                    recommendationText.setText("Grow a lettuce.\nAlmost all lettuces contain a significant amount of vitamin A,\n along with small amounts of vitamin C and iron.");
                    recommendationImage.setImage(lettuce);
                    break;
                case 4:
                    recommendationText.setText("Grow a onions.\nOnions are part of the allium family with scallions and leeks.\n When onions are cut, a compound is released\n and turns to sulfuric acid in the air.\n This is what makes people cry when \nthey cook with onions.");
                    recommendationImage.setImage(onions);
                    break;
                default:
                    recommendationText.setText("Grow a cabbages.\nCabbage contains many vitamins and minerals.\n It is high in fiber, vitamin C,\n vitamin K, potassium, calcium, and iron. ");
                    recommendationImage.setImage(cabbage);
                    break;
            }

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



