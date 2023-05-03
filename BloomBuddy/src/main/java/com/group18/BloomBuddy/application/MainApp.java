package com.group18.BloomBuddy.application;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage stage;
    public MainApp () throws Exception {
        this.stage = new Stage();
        start(stage);
    }
    @Override
    public void start(Stage stage) throws Exception {
        SceneSwitcher sceneSwitcher = new SceneSwitcher(stage);
        sceneSwitcher.setLoginScene();

    }
}
