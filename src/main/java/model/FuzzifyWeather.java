package model;

import enumerate.Season;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
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

    public String getField(String fieldName) {
        switch (fieldName) {
            case "wind_velocity": return getVelocityOfWind();
            case "day_time": return getDayTime();
            case "cloudiness": return getCloudiness();
            case "dampness": return getDampness();
            case "pressure_station": return getPressureAtStationLevel();
            case "pressure_sea": return getPressureAtSeaLevel();
            case "precipitation_six": return getPrecipitationAfterSixHours();
            case "snow": return getHeightOfFallenSnow();
            //case "temperature": (;
            //case "temperature2": ();
            case "weather": return getWeather();
            default: return "";
        }
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}

