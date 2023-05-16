package com.group18.BloomBuddy;

import org.eclipse.paho.client.mqttv3.MqttException;

public class HumidityObserver implements MyObserver {

    @Override
    public void update(MyObservable subject, Object arg) throws MqttException{
        if(subject instanceof Profile){
            Profile profile = (Profile) subject;
            updateDatabase(profile, (String) arg);
                
        }
    
    }

    
    public void updateDatabase(Profile profile, String arg) throws MqttException{
        if (arg.equals("humidityThresholdLow") == true || arg.equals("humidityThresholdHigh") == true) {
            DataBaseConnection db = new DataBaseConnection();
            float value = 0;
            if (arg.equals("humidityThresholdLow")) {
                value = profile.getHumidityLowerBound();
            } else if (arg.equals("humidityThresholdHigh")) {
                value = profile.getHumidityUpperBound();
            }

            db.editSensorSettings(arg, value, profile.getId());
        }
    } 
}
