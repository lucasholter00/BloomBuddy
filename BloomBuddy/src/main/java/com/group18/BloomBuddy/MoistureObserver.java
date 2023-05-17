package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.MqttException;

public class MoistureObserver implements MyObserver {

    public MoistureObserver(){

    }

    @Override
    public void update(MyObservable subject, Object arg) throws MqttException{
        if(subject instanceof Profile && arg instanceof String){
            Profile profile = (Profile) subject;
            updateDatabase(profile, (String) arg);
                
        }
    
    }

    
    public void updateDatabase(Profile profile, String arg) throws MqttException{
        if (arg.equals("moistureThresholdHigh") == true || arg.equals("moistureThresholdHigh") == true) {
            DataBaseConnection db = new DataBaseConnection();
            float value = 0;
            if (arg.equals("moistureThresholdLow")) {
                value = profile.getMoistureLowerBound();
            } else if (arg.equals("moistureThresholdHigh")) {
                value = profile.getMoistureUpperBound();
            }

            db.editSensorSettings(arg, value, profile.getId());
        }
    } 
}
