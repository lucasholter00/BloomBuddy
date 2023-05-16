package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.DataBaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CreateAccountController extends SceneSwitcher{
    public TextField usernameTextField;
    public PasswordField accountPasswordField;
    public Button CreateAccountButton;

    public void show (Stage stage) throws IOException {
        URL fxmlResource = getClass().getResource("/createAccountScene.fxml");
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

    public void CreateAccount(ActionEvent actionEvent) throws IOException {
        if(usernameTextField.getText().isBlank() || accountPasswordField.getText().isBlank()) {
            accountPasswordField.setText("Please enter username and password");
        } else {
            DataBaseConnection dataBaseConnection = new DataBaseConnection();
            dataBaseConnection.addUser(usernameTextField.getText(), accountPasswordField.getText());
            setLoginScene(actionEvent);
        }
    }
}
