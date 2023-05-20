package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.CurrentUser;
import com.group18.BloomBuddy.Mediator;
import com.group18.BloomBuddy.Profile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Random;

public class HomeController extends SceneSwitcher {

    public Button statisticsButton;

    @FXML
    private ScrollPane scrollPane;
    public Label recommendationText;
    public Label seasonText;
    public ImageView recommendationImage;
    public Button settingsButton;
    public Button homeButton;
    public Button myPlantButton;

    private CurrentUser currentUser;

    @FXML
    public void initialize() throws InterruptedException {
        Thread.sleep(1000);
        currentUser = Mediator.getInstance().getCurrentUser();
        List<Profile> profiles = currentUser.getProfiles();
        generateProfiles(profiles);


    }

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
    @FXML
    public void changeRecommendationText() {
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
            recommendationText.setWrapText(true);
            switch (randomNumber){
                case 0:
                    recommendationText.setText("Grow a cauliflower.Cauliflower is rich in vitamin C which is good for skin, healing, and gum health");
                    recommendationImage.setImage(cauliflower);
                    break;
                case 1:
                    recommendationText.setText("Grow a kale. Kale is a good source of vitamins A, C and K. Kale also contains antioxidants, which promote general health and well-being.");
                    recommendationImage.setImage(kale);
                    break;
                case 2:
                    recommendationText.setText("Grow a broccoli. Broccoli is a cruciferous vegetable rich many nutrients. It is a good source of vitamin C, vitamin K, and vitamin A. ");
                    recommendationImage.setImage(broccoli);
                    break;
                case 3:
                    recommendationText.setText("Grow a lettuce. Almost all lettuces contain a significant amount of vitamin A, along with small amounts of vitamin C and iron.");
                    recommendationImage.setImage(lettuce);
                    break;
                case 4:
                    recommendationText.setText("Grow a onions. Onions are part of the alluvium family with scallions and leeks.  When onions are cut, a compound is released  and turns to sulfuric acid in the air. This is what makes people cry when they cook with onions.");
                    recommendationImage.setImage(onions);
                    break;
                default:
                    recommendationText.setText("Grow a cabbages. Cabbage contains many vitamins and minerals. It is high in fiber, vitamin C, vitamin K, potassium, calcium, and iron. ");
                    recommendationImage.setImage(cabbage);
                    break;
            }

        } else if(now.isAfter(summerStart) && now.isBefore(autumnStart)) {
            seasonText.setText("Summer");
            recommendationText.setText("Grow a strawberry");
        } else if(now.isAfter(autumnStart) && now.isBefore(winterStart)) {
            seasonText.setText("Autumn");
            recommendationText.setText("Grow a pumpkin");
        } else {
            seasonText.setText("Winter");
            recommendationText.setText("Grow a ice bear");
        }
    }
    private void generateProfiles(List<Profile> profileList) {
        if (currentUser == null) {
            currentUser = Mediator.getInstance().getCurrentUser();
        }

        try {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            hbox.setPadding(new Insets(10));

            for (Profile profile : profileList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/homePagePlantCard.fxml"));

                VBox profileBox = fxmlLoader.load();
                PlantHomePageCardController plantCardController = fxmlLoader.getController();
                plantCardController.setData(profile);

                hbox.getChildren().add(profileBox);
            }

            scrollPane.setContent(hbox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}



