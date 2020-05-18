package utils;

import java.util.Arrays;
import java.util.List;

public class Consts {
    public final static String INPUT_FCL_NAME = "weather.fcl";
    public final static String ABSOLUTE_VAR_NAME = "absolute";
    public final static String RELATIVE_VAR_NAME = "percentage";
    public final static List<String> WEATHER_VAR_NAMES = Arrays.asList(
            "day_time", "cloudiness", "dampness", "wind_velocity",
            "precipitation_six", "snow", "pressure_station", "pressure_sea", "weather",
            "temperature", "temperature_wet");
    public final static List<String> ALL_VAR_NAMES = Arrays.asList(
            "day_time", "cloudiness", "dampness", "wind_velocity",
            "precipitation_six", "snow", "pressure_station", "pressure_sea", "weather",
            "temperature", "temperature_wet", "absolute", "percentage");
}
