package com.group18.BloomBuddy;

public class ProfileObserver implements MyObserver{


    @Override
    public void update(MyObservable subject, Object arg) {
        if(subject instanceof CurrentUser && arg instanceof Profile){
            addToDatabase((Profile) arg, ((CurrentUser) subject).getUsername());
        }
    }

    public void addToDatabase(Profile profile, String username){
        DataBaseConnection db = new DataBaseConnection();
        db.addProfile(profile, username);
        db.close();
    }
    
}
