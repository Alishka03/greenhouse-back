package kz.iitu.smartgreenhouse.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AnalyticResponseDTO {
    private List<AveragesPerDay> averagesPerDay;

    public AnalyticResponseDTO(List<AveragesPerDay> averagesPerDay) {
        this.averagesPerDay = averagesPerDay;
    }

    public List<AveragesPerDay> getAveragesPerDay() {
        return averagesPerDay;
    }

    public static class AveragesPerDay {
        private LocalDateTime date;
        private Averages averages;

        public AveragesPerDay(LocalDateTime date, Averages averages) {
            this.date = date;
            this.averages = averages;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public Averages getAverages() {
            return averages;
        }
    }

    public static class Averages {
        private float avgTemperature;
        private float avgCO2;
        private Float avgHumidityAir;
        private Float avgHumidityGround;
        private float avgLight;

        public Averages(float avgTemperature, float avgCO2, Float avgHumidityAir, Float avgHumidityGround, float avgLight) {
            this.avgTemperature = avgTemperature;
            this.avgCO2 = avgCO2;
            this.avgHumidityAir = avgHumidityAir;
            this.avgHumidityGround = avgHumidityGround;
            this.avgLight = avgLight;
        }

        public float getAvgTemperature() {
            return avgTemperature;
        }

        public float getAvgCO2() {
            return avgCO2;
        }

        public Float getAvgHumidityAir() {
            return avgHumidityAir;
        }

        public Float getAvgHumidityGround() {
            return avgHumidityGround;
        }

        public float getAvgLight() {
            return avgLight;
        }
    }
}
