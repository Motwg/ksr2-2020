package model;

import enumerate.Season;
import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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

    public FuzzifyWeather fuzzify(FIS fis, double activation) {
        fis.getVariable("dampness").setUniverseMax(100);
        fis.getVariable("dampness").setUniverseMin(0);
        fis.getVariable("day_time").setUniverseMax(24);
        fis.getVariable("temperature_wet_summer").setUniverseMin(0);

        fis.setVariable("day_time", getDate().getHour());
        fis.setVariable("cloudiness", getCloudiness());
        fis.setVariable("dampness", getDampness());
        fis.setVariable("wind_velocity", getVelocityOfWind());
        fis.setVariable("precipitation_six", getPrecipitationAfterSixHours());
        fis.setVariable("snow", getHeightOfFallenSnow());
        fis.setVariable("pressure_station", getPressureAtStationLevel());
        fis.setVariable("pressure_sea", getPressureAtSeaLevel());
        fis.evaluate();

        List<String> variables = Arrays.asList("day_time", "cloudiness", "dampness", "wind_velocity",
                "precipitation_six", "snow", "pressure_station", "pressure_sea", "weather");

        FuzzifyWeather fWeather = new FuzzifyWeather();

        for (String varName : variables) {
            Variable var = fis.getVariable(varName);
            String value = var.getLinguisticTerms().keySet().stream()
                    .map(str -> new Pair<>(str, var.getMembership(str)))
                    .filter(pair -> pair.getValue() > activation)
                    .max(Comparator.comparing(Pair::getValue))
                    .map(Pair::getKey)
                    .orElse("");
            fWeather.setField(varName, value);
        }
        fWeather.setSeason(getSeason());
        /*System.out.println("==================");
        System.out.println(toString());
        for( Rule r : fis.getFunctionBlock("weather").getFuzzyRuleBlock("weather_rules").getRules() )
            System.out.println(r);*/
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