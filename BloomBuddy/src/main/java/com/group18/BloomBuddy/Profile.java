package com.group18.BloomBuddy;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Profile implements myObservable {
    private SensorSettings sensorSettings;
    //Date of when the plant was last watered
    private LocalDateTime lastWatered;
    private int waterFrequency; //How often the water should be watered, in terms of days
    private List<myObserver> observers;

    public Profile(SensorSettings sensorSettings) {
        this.sensorSettings = sensorSettings;
        this.lastWatered = null; //Initialize as null, could be better ways to initialize this
        this.waterFrequency = 0; //Initialize as 0, i.e. no interval to water have been chosen yet by the user
        observers = new ArrayList<>();
        LastWateredObserver wateredObserver = new LastWateredObserver();
        addObserver(wateredObserver);

    }

    public boolean waterNeeded() {
        if (lastWatered == null) {
            //If lastWatered have not been initialized we will assume that the plant needs watering
            return true;
        }

        LocalDateTime todayDate = LocalDateTime.now();

        long elapsedTime = ChronoUnit.MILLIS.between(lastWatered, todayDate); //the time elapsed in milliseconds since last watered date

        long waterFreqToMilli = (long) waterFrequency * 24 * 60 * 60 * 1000; //24 * 60 * 60 * 1000 = numbers of miliseconds in a day (24h)

        return elapsedTime > waterFreqToMilli;

    }

    public SensorSettings getSensorSettings() {
        return sensorSettings;
    }

    public void setSensorSettings(SensorSettings sensorSettings) {
        this.sensorSettings = sensorSettings;
    }

    public LocalDateTime getLastWatered() {
        return lastWatered;
    }

    public void setLastWatered(LocalDateTime lastWatered) {
         this.lastWatered = lastWatered;
         notifyObservers();
     }


    public int getWaterFrequency() {
        return waterFrequency;
    }

    public void setWaterFrequency(int waterFrequency) {
        this.waterFrequency = waterFrequency;
    }

    @Override
    public void addObserver(myObserver observer) {
        observers.add(observer);
    }

  @Override //Can not make this function work :( It says it does not override
    public void removeObserver(myObserver observer) {
        observers.remove(observer);
    }




  @Override
  public void notifyObservers() {
      for (myObserver observer : observers) {
          observer.update(this, null);
      }
  }
}
