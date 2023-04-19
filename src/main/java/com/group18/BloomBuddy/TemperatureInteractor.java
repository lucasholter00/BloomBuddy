package com.group18.BloomBuddy;



public class TemperatureInteractor {



    private TemperatureData latestData;

    public TemperatureInteractor() {

    }



    public double getLatestData(){
        return latestData.getValue();
    }
}