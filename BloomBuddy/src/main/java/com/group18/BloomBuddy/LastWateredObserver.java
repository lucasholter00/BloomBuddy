package com.group18.BloomBuddy;

import com.mongodb.MongoException;

import java.time.LocalDateTime;

public class LastWateredObserver implements MyObserver {

    public LastWateredObserver() {
    }

    @Override
    public void update(MyObservable subject, Object arg) {
        String filter = (String)arg;
        if(subject instanceof Profile && filter.equals("lastWatered")){ //Check if the observable object is an instance of Profile, if not we do not want it to precede
            Profile profile = (Profile) subject; //Downcast the Observable object
            LocalDateTime lastWatered = profile.getLastWatered();
            //Method to insert in to the database, will be active when profile.getID exists
            updateLastWatered(lastWatered, profile.getId());
        }

    }

    private void updateLastWatered(LocalDateTime lastWatered, String profileID) throws MongoException {
        DataBaseConnection dbConn = new DataBaseConnection();

        // connect to the database here
        dbConn.insertLastWatered(lastWatered,profileID);
        dbConn.close();
    }
}
