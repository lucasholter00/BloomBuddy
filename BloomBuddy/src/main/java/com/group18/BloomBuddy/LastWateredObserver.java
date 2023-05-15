package com.group18.BloomBuddy;

import com.mongodb.MongoException;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class LastWateredObserver implements myObserver {

    public LastWateredObserver() {
    }

    @Override
    public void update(myObservable o, Object arg) {
        String filter = (String)arg;
        if(o instanceof Profile && filter.equals("lastWatered")){ //Check if the observable object is an instance of Profile, if not we do not want it to precede
            Profile profile = (Profile) o; //Downcast the Observable object
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
