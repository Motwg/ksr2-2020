package model;

import enumerate.Season;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@ToString
public class FuzzifyWeather {
    private String velocityOfWind;
    private String dayTime;
    private String cloudiness;
    private String temperature;
    private String temperatureOfWetThermometer;
    private String dampness;
    private String pressureAtStationLevel;
    private String pressureAtSeaLevel;
    private String precipitationAfterSixHours;
    private String heightOfFallenSnow;
    private String weather;
    private Season season;

    public void setField(String fieldName, String fieldValue) {
        switch (fieldName) {
            case "wind_velocity": setVelocityOfWind(fieldValue); break;
            case "day_time": setDayTime(fieldValue); break;
            case "cloudiness": setCloudiness(fieldValue); break;
            case "dampness": setDampness(fieldValue); break;
            case "pressure_station": setPressureAtStationLevel(fieldValue); break;
            case "pressure_sea": setPressureAtSeaLevel(fieldValue); break;
            case "precipitation_six": setPrecipitationAfterSixHours(fieldValue); break;
            case "snow": setHeightOfFallenSnow(fieldValue); break;
            //case "temperature": (fieldValue);
            //case "temperature2": (fieldValue);
            case "weather": setWeather(fieldValue); break;
        }
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}

