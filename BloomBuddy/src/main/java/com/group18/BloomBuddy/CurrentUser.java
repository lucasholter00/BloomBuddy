package com.group18.BloomBuddy;

import java.util.List;

public class CurrentUser implements MyObservable{

    private final String username;
    private List<MyObserver> observers;
    private List<Profile> profiles;

    public CurrentUser(String username, List<Profile> profiles) {
        this.username = username;
        this.profiles = profiles;
    }

    //add observers for name and if profile is added

    public String getUsername() {
        return username;
    }

    public List<Profile> getProfiles() {
        return profiles;
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

    public void notifyObservers(Object arg){
        for(MyObserver observer : observers){
            this.notifyObservers(arg);
        }
    }


    
}
