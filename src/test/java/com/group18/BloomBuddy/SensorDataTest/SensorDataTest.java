package com.group18.BloomBuddy.SensorDataTest;
import com.group18.BloomBuddy.SensorData.HumidityData;
import com.group18.BloomBuddy.SensorData.LightData;
import com.group18.BloomBuddy.SensorData.MoistureData;
import com.group18.BloomBuddy.SensorData.TemperatureData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class SensorDataTest {




        @Test
        public void testTemperatureValue() {
            TemperatureData temperatureData = new TemperatureData(25.0F);
            assertEquals(25.0, temperatureData.getValue());
        }




        @Test
        public void testMoistureValue() {
            MoistureData moistureData = new MoistureData(0.5F);
            assertEquals(0.5F, moistureData.getValue());
        }




        @Test
        public void testLightValue() {
            LightData lightData = new LightData(1000.0F);
            assertEquals(1000.0F, lightData.getValue());
        }




        @Test
        public void testHumidityValue() {
            HumidityData humidityData = new HumidityData(0.8F);
            assertEquals(0.8F, humidityData.getValue());
        }

}
