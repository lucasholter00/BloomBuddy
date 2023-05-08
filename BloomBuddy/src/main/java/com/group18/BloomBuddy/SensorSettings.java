package com.group18.BloomBuddy;

import java.util.Arrays;
import java.util.List;

public class SensorSettings {
        private float temperatureLowerBound;
        private float temperatureUpperBound;
        private float moistureLowerBound;
        private float moistureUpperBound;
        private float lightLowerBound;
        private float lightUpperBound;
        private float humidityLowerBound;
        private float humidityUpperBound;

        public SensorSettings(float temperatureLowerBound, float temperatureUpperBound,
                              float moistureLowerBound, float moistureUpperBound,
                              float lightLowerBound, float lightUpperBound,
                              float humidityLowerBound, float humidityUpperBound) {
            this.temperatureLowerBound = temperatureLowerBound;
            this.temperatureUpperBound = temperatureUpperBound;
            this.moistureLowerBound = moistureLowerBound;
            this.moistureUpperBound = moistureUpperBound;
            this.lightLowerBound = lightLowerBound;
            this.lightUpperBound = lightUpperBound;
            this.humidityLowerBound = humidityLowerBound;
            this.humidityUpperBound = humidityUpperBound;
        }

    public List<Boolean> checkSensorReadings(SensorData sensorData) {
        float temperature = sensorData.getTemperature();
        float moisture = sensorData.getMoistureLevel();
        float light = sensorData.getLightIntensity();
        float humidity = sensorData.getHumidity();

        boolean temperatureOutOfBounds = temperature <= temperatureLowerBound || temperature >= temperatureUpperBound;
        boolean moistureOutOfBounds = moisture <= moistureLowerBound || moisture >= moistureUpperBound;
        boolean lightOutOfBounds = light <= lightLowerBound || light >= lightUpperBound;
        boolean humidityOutOfBounds = humidity <= humidityLowerBound || humidity >= humidityUpperBound;

        return Arrays.asList(temperatureOutOfBounds, moistureOutOfBounds, lightOutOfBounds, humidityOutOfBounds);
    }


        public float getTemperatureLowerBound() {
            return temperatureLowerBound;
        }

        public float getTemperatureUpperBound() {
            return temperatureUpperBound;
        }

        public float getMoistureLowerBound() {
            return moistureLowerBound;
        }

        public float getMoistureUpperBound() {
            return moistureUpperBound;
        }

        public float getLightLowerBound() {
            return lightLowerBound;
        }

        public float getLightUpperBound() {
            return lightUpperBound;
        }

        public float getHumidityLowerBound() {
            return humidityLowerBound;
        }

        public float getHumidityUpperBound() {
            return humidityUpperBound;
        }

        public void setTemperatureLowerBound(float temperatureLowerBound) {this.temperatureLowerBound = temperatureLowerBound;}

        public void setTemperatureUpperBound(float temperatureUpperBound) {this.temperatureUpperBound = temperatureUpperBound;}

        public void setMoistureLowerBound(float moistureLowerBound) {
            this.moistureLowerBound = moistureLowerBound;
        }

        public void setMoistureUpperBound(float moistureUpperBound) {
            this.moistureUpperBound = moistureUpperBound;
        }

        public void setLightLowerBound(float lightLowerBound) {
            this.lightLowerBound = lightLowerBound;
        }

        public void setLightUpperBound(float lightUpperBound) {
            this.lightUpperBound = lightUpperBound;
        }

        public void setHumidityLowerBound(float humidityLowerBound) {
            this.humidityLowerBound = humidityLowerBound;
        }

        public void setHumidityUpperBound(float humidityUpperBound) {
            this.humidityUpperBound = humidityUpperBound;
        }
    }


