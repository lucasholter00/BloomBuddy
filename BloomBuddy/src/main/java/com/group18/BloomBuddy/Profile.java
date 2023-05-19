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

    public Profile(SensorSettings sensorSettings, String name) throws MqttException {
        this.sensorSettings = sensorSettings;
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.historicalData = new ArrayList<HistoricalData>();
        this.lastWatered = null; //Initialize as null, could be better ways to initialize this
        this.waterFrequency = 0; //Initialize as 0, i.e. no interval to water have been chosen yet by the user
        observers = new ArrayList<>();
        MyObserver wateredObserver = new LastWateredObserver();
        MyObserver needsWaterObserver = new NeedsWaterObserver();
        MyObserver temperatureObserver = new TemperatureObserver();
        MyObserver humidityObserver = new HumidityObserver();
        MyObserver lightObserver = new LightObserver();
        MyObserver moistureObserver= new MoistureObserver();
        MyObserver historicalDataObserver = new HistoricalDataObserver();
        addObserver(wateredObserver);
        addObserver(moistureObserver);
        addObserver(needsWaterObserver);
        addObserver(temperatureObserver); 
        addObserver(humidityObserver);
        addObserver(lightObserver);
        addObserver(historicalDataObserver);

    }

    public Profile(SensorSettings sensorSettings, String name, String id) throws MqttException {
        this.sensorSettings = sensorSettings;
        this.name = name;
        this.id = id;
        this.historicalData = new ArrayList<HistoricalData>();
        this.lastWatered = null; //Initialize as null, could be better ways to initialize this
        this.waterFrequency = 0; //Initialize as 0, i.e. no interval to water have been chosen yet by the user
        observers = new ArrayList<>();
        MyObserver wateredObserver = new LastWateredObserver();
        MyObserver needsWaterObserver = new NeedsWaterObserver();
        MyObserver temperatureObserver = new TemperatureObserver();
        MyObserver humidityObserver = new HumidityObserver();
        MyObserver lightObserver = new LightObserver();
        MyObserver moistureObserver = new MoistureObserver();
        MyObserver historicalDataObserver = new HistoricalDataObserver();
        addObserver(moistureObserver);
        addObserver(wateredObserver);
        addObserver(needsWaterObserver);
        addObserver(temperatureObserver);
        addObserver(humidityObserver);
        addObserver(lightObserver);
        addObserver(historicalDataObserver);

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

    public void newHistoricalData(HistoricalData data) throws MqttException {
        this.historicalData.add(data);
        notifyObservers(data);
    }

    public HistoricalData getHistoricalData(int index) {
        return this.historicalData.get(index);
    }

    

    public void recieveWatered(){


    }

        public SensorSettings getSensorSettings() {
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

        public void setName (String newName) throws MqttException{
            this.name = newName;
            notifyObservers("name");
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
        public void notifyObservers (Object arg) throws MqttException {
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

    public void setTemperatureUpperBound(float temperatureUpperBound) throws MqttException {
        sensorSettings.setTemperatureUpperBound(temperatureUpperBound);
        notifyObservers("tempratureThresholdHigh");;
    }

    public void setTemperatureLowerBound(float temperatureLowerBound) throws MqttException {
        sensorSettings.setTemperatureLowerBound(temperatureLowerBound);
        notifyObservers("tempratureThresholdLow");
    }

    public void setHumidityUpperBound(float humidityUpperBound) throws MqttException {
        sensorSettings.setHumidityUpperBound(humidityUpperBound);
        notifyObservers("humidityThresholdHigh");
    }

    public void setHumidityLowerBound(float humidityLowerBound) throws MqttException {
        sensorSettings.setHumidityLowerBound(humidityLowerBound);
        notifyObservers("humidityThresholdLow");
    }

    public void setLightUpperBound(float lightUpperBound) throws MqttException{
        sensorSettings.setLightUpperBound(lightUpperBound);
        notifyObservers("lightThresholdHigh");
    }

    public void setLightLowerBound(float lightLowerBound) throws MqttException{
        sensorSettings.setLightLowerBound(lightLowerBound);
        notifyObservers("lightThresholdLow");
    }

    public void setMoistureUpperBound(float moistureUpperBound) throws MqttException{
        sensorSettings.setMoistureUpperBound(moistureUpperBound);
        notifyObservers("moistureThresholdHigh");
    }

    public void setMoistureLowerBound(float moistureLowerBound) throws MqttException{
        sensorSettings.setMoistureLowerBound(moistureLowerBound);
        notifyObservers("moistureThresholdLow");
    }

    public float getTemperatureUpperBound() throws MqttException{
        return sensorSettings.getTemperatureUpperBound();
    }

    public float getTemperatureLowerBound() throws MqttException{
        return sensorSettings.getTemperatureLowerBound();
    }

    public float getHumidityUpperBound() throws MqttException{
        return sensorSettings.getHumidityUpperBound();
    }

    public float getHumidityLowerBound() throws MqttException{
        return sensorSettings.getHumidityLowerBound();
    }

    public float getLightUpperBound() throws MqttException{
        return sensorSettings.getLightUpperBound();
    }

    public float getLightLowerBound() throws MqttException{
        return sensorSettings.getLightLowerBound();
    }

    public float getMoistureUpperBound() throws MqttException{
        return sensorSettings.getMoistureUpperBound();
    }

    public float getMoistureLowerBound() throws MqttException{
        return sensorSettings.getMoistureLowerBound();
    }

    public String toString() {
        return "Plant{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", lastWatered=" + lastWatered +
                ", waterFrequency=" + waterFrequency +
                ", sensorSettings=" + sensorSettings +
                ", historicalData=" + historicalData +
                '}';
    }




}
