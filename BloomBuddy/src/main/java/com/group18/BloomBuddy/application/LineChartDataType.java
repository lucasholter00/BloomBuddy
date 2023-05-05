package com.group18.BloomBuddy.application;

import com.group18.BloomBuddy.SensorData;

import java.util.function.Function;

public enum LineChartDataType {
    TEMPERATURE("Temperature (°C)", SensorData::getTemperature),
    MOISTURE("Moisture", SensorData::getMoisture_level),
    HUMIDITY("Humidity (%)", SensorData::getHumidity),
    LIGHT("Light Level", SensorData::getLight_intensity);

    //The Y-axis label for the chart.
    private final String yAxisLabel;
    //The sensorValueFunction is used to retrieve the data value associated with the enum.
    private final Function<SensorData, Float> sensorValueFunction;

    LineChartDataType(String yAxisLabel, Function<SensorData, Float> sensorValueFunction) {
        this.yAxisLabel = yAxisLabel;
        this.sensorValueFunction = sensorValueFunction;
    }

    public String getYAxisLabel() {
        return yAxisLabel;
    }

    public Float getSensorValue(SensorData data) {
        return sensorValueFunction.apply(data);
    }
}
