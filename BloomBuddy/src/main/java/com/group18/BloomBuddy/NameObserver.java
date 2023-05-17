package com.group18.BloomBuddy;

public class NameObserver implements MyObserver{

    public NameObserver(){

    }

    @Override
    public void update(MyObservable subject, Object arg){
        if(subject instanceof Profile && arg instanceof String){
            Profile profile = (Profile) subject;
            updateDatabase(profile, (String) arg);
        }
    
    }

    public void updateDatabase(Profile profile, String arg){
        if(arg.equals("name") == true){
            DataBaseConnection db = new DataBaseConnection();
            String value = profile.getName();
            db.editProfileName(value, profile.getId());
        }
    }

    
}
