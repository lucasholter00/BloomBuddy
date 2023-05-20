package com.group18.BloomBuddy;

import javafx.stage.Stage;

// The mediator is used to make the currentUser and the profiles wanted to be edited
// accessible for all controllers.
public class Mediator {
    private static Mediator INSTANCE;

    private Stage stage;
    private CurrentUser currentUser;
    private Profile editProfile;

    public static Mediator getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Mediator();
        }
        return INSTANCE;
    }

    private Mediator() {
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }
    
    public Profile getEditProfile() {
        return editProfile;
    }

    public void setEditProfile(Profile editProfile) {
        this.editProfile = editProfile;
    }
}
