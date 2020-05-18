package model;

//import depr.DeprFuzzifyWeather;
import enumerate.Season;
import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import utils.Consts;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
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
    private double heightOfFallenSnow;
    private Season season;

    public Weather(int velocityOfWind, LocalDateTime date, double cloudiness, double temperature,
                   double temperatureOfWetThermometer, int dampness, double pressureAtStationLevel,
                   double pressureAtSeaLevel, double precipitationAfterSixHours, double heightOfFallenSnow) {
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

    public SimpleFuzzifyWeather fuzzify(FIS fis) {
        fis.setVariable("day_time", getDate().getHour());
        fis.setVariable("cloudiness", getCloudiness());
        fis.setVariable("dampness", getDampness());
        fis.setVariable("wind_velocity", getVelocityOfWind());
        fis.setVariable("precipitation_six", getPrecipitationAfterSixHours());
        fis.setVariable("snow", getHeightOfFallenSnow());
        fis.setVariable("pressure_station", getPressureAtStationLevel());
        fis.setVariable("pressure_sea", getPressureAtSeaLevel());
        fis.setVariable("temperature_" + getSeason().name().toLowerCase(), getTemperature());
        fis.setVariable("temperature_wet_" + getSeason().name().toLowerCase(), getTemperatureOfWetThermometer());
        fis.evaluate();

        List<String> variables = Consts.WEATHER_VAR_NAMES;

        SimpleFuzzifyWeather fWeather = new SimpleFuzzifyWeather();
        for (String varName : variables) {
            Variable var;
            if (varName.contains("temperature")) {
                var = fis.getVariable(varName + '_' + getSeason().name().toLowerCase());
            }else {
                var = fis.getVariable(varName);
            }
            List<Pair<String, Double>> terms = var.getLinguisticTerms().keySet().stream()
                    .map(str -> new Pair<>(str, var.getMembership(str)))
                    .collect(Collectors.toList());
            //fWeather.setField(varName, value);
            fWeather.addTerms(terms);
        }
        fWeather.setSeason(getSeason());
        return fWeather;
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