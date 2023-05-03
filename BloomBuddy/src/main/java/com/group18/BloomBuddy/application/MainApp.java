package com.group18.BloomBuddy.application;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneSwitcher sceneSwitcher = new SceneSwitcher(primaryStage);
        sceneSwitcher.setStatScene();

    }
    public static void main(String[] args){
        launch(args);
    }
}
