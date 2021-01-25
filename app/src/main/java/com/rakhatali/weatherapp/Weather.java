package com.rakhatali.weatherapp;

import java.util.Date;

public class Weather {
    String city, description;
    double temperature, feelsLike;
    Date added_time;
    public Weather(){}

    public Weather(String city, String description, double temperature, double feelsLike, Date added_time) {
        this.city = city;
        this.description = description;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.added_time = added_time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Date getAdded_time() {
        return added_time;
    }

    public void setAdded_time(Date added_time) {
        this.added_time = added_time;
    }
}
