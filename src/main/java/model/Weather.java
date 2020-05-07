package model;

import enumerate.Season;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

public class Weather {

    private int velocityOfWind;
    private LocalDateTime date;
    private double cloudiness;
    private double temperature;
    private double temperatureOfWetThermometer;
    private int dampness;
    private double pressureAtStationLevel;
    private double pressureAtSeaLevel;
    private double precipitationAfterSixHours;
    private int heightOfFallenSnow;
    private Season season;

    public Weather(int velocityOfWind, LocalDateTime date, double cloudiness, double temperature,
                   double temperatureOfWetThermometer, int dampness, double pressureAtStationLevel,
                   double pressureAtSeaLevel, double precipitationAfterSixHours, int heightOfFallenSnow) {
        this.velocityOfWind = velocityOfWind;
        this.date = date;
        this.cloudiness = cloudiness;
        this.temperature = temperature;
        this.temperatureOfWetThermometer = temperatureOfWetThermometer;
        this.dampness = dampness;
        this.pressureAtStationLevel = pressureAtStationLevel;
        this.pressureAtSeaLevel = pressureAtSeaLevel;
        this.precipitationAfterSixHours = precipitationAfterSixHours;
        this.heightOfFallenSnow = heightOfFallenSnow;
        this.season = SeasonFactory.getSeason(date);
    }

    public int getVelocityOfWind() {
        return velocityOfWind;
    }

    public void setVelocityOfWind(int velocityOfWind) {
        this.velocityOfWind = velocityOfWind;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(double cloudiness) {
        this.cloudiness = cloudiness;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperatureOfWetThermometer() {
        return temperatureOfWetThermometer;
    }

    public void setTemperatureOfWetThermometer(double temperatureOfWetThermometer) {
        this.temperatureOfWetThermometer = temperatureOfWetThermometer;
    }

    public int getDampness() {
        return dampness;
    }

    public void setDampness(int dampness) {
        this.dampness = dampness;
    }

    public double getPressureAtStationLevel() {
        return pressureAtStationLevel;
    }

    public void setPressureAtStationLevel(double pressureAtStationLevel) {
        this.pressureAtStationLevel = pressureAtStationLevel;
    }

    public double getPressureAtSeaLevel() {
        return pressureAtSeaLevel;
    }

    public void setPressureAtSeaLevel(double pressureAtSeaLevel) {
        this.pressureAtSeaLevel = pressureAtSeaLevel;
    }

    public double getPrecipitationAfterSixHours() {
        return precipitationAfterSixHours;
    }

    public void setPrecipitationAfterSixHours(double precipitationAfterSixHours) {
        this.precipitationAfterSixHours = precipitationAfterSixHours;
    }

    public int getHeightOfFallenSnow() {
        return heightOfFallenSnow;
    }

    public void setHeightOfFallenSnow(int heightOfFallenSnow) {
        this.heightOfFallenSnow = heightOfFallenSnow;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "velocityOfWind=" + velocityOfWind +
                ", date=" + date +
                ", cloudiness=" + cloudiness +
                ", temperature=" + temperature +
                ", temperatureOfWetThermometer=" + temperatureOfWetThermometer +
                ", dampness=" + dampness +
                ", pressureAtStationLevel=" + pressureAtStationLevel +
                ", pressureAtSeaLevel=" + pressureAtSeaLevel +
                ", precipitationAfterSixHours=" + precipitationAfterSixHours +
                ", heightOfFallenSnow=" + heightOfFallenSnow +
                ", season=" + season +
                '}';
    }
}