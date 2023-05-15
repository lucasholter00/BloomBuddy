package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class Profile implements myObservable {
    private SensorSettings sensorSettings;
    private String name;
    private String id;
    private ArrayList<HistoricalData> historicalData;
    //Date of when the plant was last watered
    private LocalDateTime lastWatered;
    private int waterFrequency; //How often the water should be watered, in terms of days
    private List<myObserver> observers;

    public Profile(SensorSettings sensorSettings, String name) {
        this.sensorSettings = sensorSettings;
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.historicalData = new ArrayList<HistoricalData>();
        this.lastWatered = null; //Initialize as null, could be better ways to initialize this
        this.waterFrequency = 0; //Initialize as 0, i.e. no interval to water have been chosen yet by the user
        observers = new ArrayList<>();
        LastWateredObserver wateredObserver = new LastWateredObserver();
        needsWaterObserver needsWaterObserver = new needsWaterObserver();
        addObserver(wateredObserver);
        addObserver(needsWaterObserver);

    }

    public boolean waterNeeded() throws MqttException { //This method needs to be checked in the app in order for the functionality to work.
        if (lastWatered == null) {
            //If lastWatered have not been initialized we will assume that the plant needs watering
            return true;
        }

        LocalDateTime todayDate = LocalDateTime.now();

        long elapsedTime = ChronoUnit.MILLIS.between(lastWatered, todayDate); //the time elapsed in milliseconds since last watered date

        long waterFreqToMilli = (long) waterFrequency * 24 * 60 * 60 * 1000; //24 * 60 * 60 * 1000 = numbers of miliseconds in a day (24h)

        if (elapsedTime > waterFreqToMilli) {
            notifyObservers("needsWater");
            return true;
        }else {
            return false;
        }
    }

    public void addHistoricalData(HistoricalData data) {
        this.historicalData.add(data);
    }

        public SensorSettings getSensorSettings () {
            return sensorSettings;
        }

        public void setSensorSettings (SensorSettings sensorSettings){
            this.sensorSettings = sensorSettings;
        }

        public String getName () {
            return name;
        }

        public String getId () {
            return id;
        }

        public void setName (String newName){
            this.name = newName;
        }

        public LocalDateTime getLastWatered () {
            return lastWatered;
        }

        public void setLastWatered (LocalDateTime lastWatered) throws MqttException {
            this.lastWatered = lastWatered;
            notifyObservers("lastWatered");
        }


        public int getWaterFrequency () {
            return waterFrequency;
        }

        public void setWaterFrequency ( int waterFrequency){
            this.waterFrequency = waterFrequency;
        }

        @Override
        public void addObserver (myObserver observer){
            observers.add(observer);
        }

        @Override
        public void removeObserver (myObserver observer){
            observers.remove(observer);
        }


        @Override
        public void notifyObservers (String arg) throws MqttException {
            for (myObserver observer : observers) {
                observer.update(this, arg);
            }
        }
    }
