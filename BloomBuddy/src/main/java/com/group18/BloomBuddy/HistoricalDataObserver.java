package com.group18.BloomBuddy;

public class HistoricalDataObserver implements MyObserver{

    public HistoricalDataObserver(){

    }

    @Override
    public void update(MyObservable subject, Object arg){
        if(subject instanceof Profile && arg instanceof HistoricalData){
            Profile profile = (Profile) subject;
            updateDatabase(profile, arg);
        }
    }

    public void updateDatabase(Profile profile, Object arg){
        if(arg instanceof HistoricalData){
            DataBaseConnection db = new DataBaseConnection();
            HistoricalData value = (HistoricalData) arg;
            db.insertHistoricalData(value, profile.getId());
            db.close();
        }
    }
    
    
}
