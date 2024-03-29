package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.CurrentUser;
import com.group18.BloomBuddy.DataBaseConnection;
import com.group18.BloomBuddy.Mediator;
import com.group18.BloomBuddy.Profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

    //Checks if the fields are correctly prompted.
    public void loginButtonOnAction(ActionEvent actionEvent) throws IOException, MqttException {
        CurrentUser currentUser = validateLogin();
        if(usernameTextField.getText().isBlank() || passwordPasswordField.getText().isBlank()){
            loginLabel.setText("Please enter username and password");
        } if(currentUser == null) {
            loginLabel.setText("Please enter a valid username or password");
        } else {

            Mediator.getInstance().setCurrentUser((currentUser));
            generateProfiles();
            setHomeScene(actionEvent);

        }
    }

    //Checks if the login details are valid and then passes the valid user to be stored as the currentUser, while the
    // app is running or until the user logs out.
    public CurrentUser validateLogin() throws MqttException {
        DataBaseConnection dataBaseConnection = new DataBaseConnection();
        CurrentUser currentUser = new CurrentUser(usernameTextField.getText(),dataBaseConnection.getProfiles(usernameTextField.getText()));
        if (!dataBaseConnection.verifyLogin(usernameTextField.getText(), passwordPasswordField.getText())){
            currentUser = null;
        }
        return currentUser;
    }
    private void generateProfiles(){


        CurrentUser user = Mediator.getInstance().getCurrentUser();

        if(user.getProfiles().size() > 0) {
            user.setCurrentProfile(user.getProfiles().get(0));
        }
    }

}
