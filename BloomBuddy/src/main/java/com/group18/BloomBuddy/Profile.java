
package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class Profile implements MyObservable {
    private SensorSettings sensorSettings;
    private String name;
    private String id;
    private ArrayList<HistoricalData> historicalData;
    //Date of when the plant was last watered
    private LocalDateTime lastWatered;
    private int waterFrequency; //How often the water should be watered, in terms of days
    private List<MyObserver> observers;

    MQTTHandler mqttHandler;

    public Profile(SensorSettings sensorSettings, String name) throws MqttException {
        this.sensorSettings = sensorSettings;
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.historicalData = new ArrayList<HistoricalData>();
        this.lastWatered = null; //Initialize as null, could be better ways to initialize this
        this.waterFrequency = 0; //Initialize as 0, i.e. no interval to water have been chosen yet by the user
        observers = new ArrayList<>();
        LastWateredObserver wateredObserver = new LastWateredObserver();
        NeedsWaterObserver needsWaterObserver = new NeedsWaterObserver();
        addObserver(wateredObserver);
        addObserver(needsWaterObserver);
       mqttHandler = createMQTTHandler();

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

    public void recieveWatered(){


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
        public void addObserver (MyObserver observer){
            observers.add(observer);
        }

        @Override
        public void removeObserver (MyObserver observer){
            observers.remove(observer);
        }


        @Override
        public void notifyObservers (String arg) throws MqttException {
            for (MyObserver observer : observers) {
                observer.update(this, arg);
            }
        }

        private MQTTHandler createMQTTHandler() throws MqttException {
        MQTTHandler mqttHandler = new MQTTHandler(MqttCallback());
        mqttHandler.subscribe("BloomBuddy/lastWatered");

        return mqttHandler;
        }
    private MqttCallback MqttCallback (){
        MqttCallback mqttCallback = new MqttCallback() {
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost: " + cause.getMessage());
            }

            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if (topic.equals("BloomBuddy/lastWatered")){
                    if(new String(message.getPayload()).equals("Watered")){
                        setLastWatered(LocalDateTime.now());
                    }
                }
            }

            public void deliveryComplete(IMqttDeliveryToken token) {
                // not used in this example
            }
        };

        return mqttCallback;
    }
    }

