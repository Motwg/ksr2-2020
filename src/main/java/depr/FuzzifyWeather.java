package depr;

import enumerate.Season;
import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FuzzifyWeather {
    private List<Pair<String, Double>> velocityOfWind;
    private List<Pair<String, Double>> dayTime;
    private List<Pair<String, Double>> cloudiness;
    private List<Pair<String, Double>> temperature;
    private List<Pair<String, Double>> temperatureOfWetThermometer;
    private List<Pair<String, Double>> dampness;
    private List<Pair<String, Double>> pressureAtStationLevel;
    private List<Pair<String, Double>> pressureAtSeaLevel;
    private List<Pair<String, Double>> precipitationAfterSixHours;
    private List<Pair<String, Double>> heightOfFallenSnow;
    private List<Pair<String, Double>> weather;
    private Season season;

    public void setField(String fieldName, List<Pair<String, Double>> fieldValue) {
        switch (fieldName) {
            case "wind_velocity": setVelocityOfWind(fieldValue); break;
            case "day_time": setDayTime(fieldValue); break;
            case "cloudiness": setCloudiness(fieldValue); break;
            case "dampness": setDampness(fieldValue); break;
            case "pressure_station": setPressureAtStationLevel(fieldValue); break;
            case "pressure_sea": setPressureAtSeaLevel(fieldValue); break;
            case "precipitation_six": setPrecipitationAfterSixHours(fieldValue); break;
            case "snow": setHeightOfFallenSnow(fieldValue); break;
            case "temperature": setTemperature(fieldValue); break;
            case "temperature_wet": setTemperatureOfWetThermometer(fieldValue); break;
            case "weather": setWeather(fieldValue); break;
        }
    }

    public List<Pair<String, Double>> getField(String fieldName) {
        switch (fieldName) {
            case "wind_velocity": return getVelocityOfWind();
            case "day_time": return getDayTime();
            case "cloudiness": return getCloudiness();
            case "dampness": return getDampness();
            case "pressure_station": return getPressureAtStationLevel();
            case "pressure_sea": return getPressureAtSeaLevel();
            case "precipitation_six": return getPrecipitationAfterSixHours();
            case "snow": return getHeightOfFallenSnow();
            case "temperature": return getTemperature();
            case "temperature_wet": return  getTemperatureOfWetThermometer();
            case "weather": return getWeather();
            default: return null;
        }
    }
}
