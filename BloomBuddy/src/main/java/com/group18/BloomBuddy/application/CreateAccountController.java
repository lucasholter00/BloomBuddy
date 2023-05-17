package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.DataBaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CreateAccountController extends SceneSwitcher{
    public TextField usernameTextField;
    public PasswordField accountPasswordField;
    public Button CreateAccountButton;
    public Label createAccountLabel;

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
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        createAccountLabel.setAlignment(Pos.CENTER);
        if (usernameTextField.getText().isBlank() || accountPasswordField.getText().isBlank()) {
            createAccountLabel.setText("Please enter username and password");
        } else if (!dataBaseConnection.addUser(usernameTextField.getText(), accountPasswordField.getText())){
            createAccountLabel.setText("Username is already in use");
        } else {
            dataBaseConnection.addUser(usernameTextField.getText(), accountPasswordField.getText());
            setLoginScene(actionEvent);
        }
    }
}
