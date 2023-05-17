package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.MqttException;

public class LightObserver implements MyObserver {
        
    public LightObserver(){
        
    }

    @Override
    public void update(MyObservable subject, Object arg) throws MqttException{
        if(subject instanceof Profile && arg instanceof String){
            Profile profile = (Profile) subject;
            updateDatabase(profile, (String) arg);
                
        }
    
    }

    
    public void updateDatabase(Profile profile, String arg) throws MqttException{
        if (arg.equals("lightThresholdLow") == true || arg.equals("lightThresholdHigh") == true) {
            DataBaseConnection db = new DataBaseConnection();
            float value = 0;
            if (arg.equals("lightThresholdLow")) {
                value = profile.getLightLowerBound();
            } else if (arg.equals("lightThresholdHigh")) {
                value = profile.getLightUpperBound();
            }

            db.editSensorSettings(arg, value, profile.getId());
        }
    } 
}
