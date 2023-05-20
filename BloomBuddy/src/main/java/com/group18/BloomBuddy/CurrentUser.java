package com.group18.BloomBuddy;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttException;

public class CurrentUser implements MyObservable{

    private final String username;

    private Profile currentProfile;
    private List<MyObserver> observers;
    private List<Profile> profiles;

    public CurrentUser(String username, List<Profile> profiles) {
        this.username = username;
        this.profiles = profiles;
        this.observers = new ArrayList<MyObserver>();
        MyObserver observer = new ProfileObserver();
        observers.add(observer);
    }

    //add observers for name and if profile is added

    public String getUsername() {
        return username;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void newProfile(Profile profile) throws MqttException{
        profiles.add(profile);
        notifyObservers(profile);
    }

    public void addProfile(Profile profile) {
        profiles.add(profile);
    }

    public Profile getProfile(String profileId){
        for(Profile profile : profiles){
            if(profile.getId().equals(profileId)){
                return profile;
            }
        }
        return null;
    }



    public Profile getCurrentProfile() {
        return currentProfile;
    }

    // Setter for currentProfile
    public void setCurrentProfile(Profile currentProfile) {
        this.currentProfile = currentProfile;
    }

    public boolean isActive(Profile profile) {
        return profile == currentProfile;
    }
    
    @Override
    public void addObserver(MyObserver observer){
        for(Profile profile : profiles){
            profile.addObserver(observer);
        }
    }

    public void removeObserver(MyObserver observer){
        for(Profile profile : profiles){
            profile.removeObserver(observer);
        }
    }

    public void notifyObservers(Object arg) throws MqttException{
        for(MyObserver observer : observers){
            observer.update((MyObservable)this, arg);
        }
    }


    
}
