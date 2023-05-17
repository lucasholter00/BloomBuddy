package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.DataBaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
public class LoginController extends SceneSwitcher{


    public Button loginButton;
    public PasswordField passwordPasswordField;
    public TextField usernameTextField;
    public Label loginLabel;
    public Button registerAccountButton;

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

    public void loginButtonOnAction(ActionEvent actionEvent) throws IOException {
        if(usernameTextField.getText().isBlank() || passwordPasswordField.getText().isBlank()){
            loginLabel.setText("Please enter username and password");
        } else if(!validateLogin()) {
            loginLabel.setText("Please enter a valid username or password");
        } else {
            setHomeScene(actionEvent);
        }
    }

    public boolean validateLogin(){
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        return dataBaseConnection.verifyLogin(usernameTextField.getText(),passwordPasswordField.getText());
    }
}
