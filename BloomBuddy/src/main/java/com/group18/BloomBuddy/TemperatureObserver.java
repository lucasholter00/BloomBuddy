package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.MqttException;

public class TemperatureObserver implements MyObserver {

    public TemperatureObserver(){
    }
    @Override
    public void update(MyObservable subject, Object arg) throws MqttException{
        if(subject instanceof Profile && arg instanceof String){
            Profile profile = (Profile) subject;
            updateDatabase(profile, (String) arg);
                
        }
    
    }

    
    public void updateDatabase(Profile profile, String arg) throws MqttException{
        if(arg.equals("tempratureThresholdLow") || arg.equals("tempratureThresholdHigh")){
            DataBaseConnection db = new DataBaseConnection();
            float value = 0;
            if(arg.equals("tempratureThresholdLow")){
                value = profile.getTemperatureLowerBound();
            }
            else {
                value = profile.getTemperatureUpperBound();
            }

            db.editSensorSettings(arg, value, profile.getId());
            db.close();
        }
    }
    
}
