package com.programstructure;

public class WeatherRecord {
    public String station;
    public double temperature;
    public double humidity;

    public WeatherRecord(String station, double temperature, double humidity) {
        this.station = station;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return station + "," + temperature + "," + humidity;
    }

    public static WeatherRecord fromString(String line) {
        String[] parts = line.split(",");
        return new WeatherRecord(parts[0], Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
    }
}