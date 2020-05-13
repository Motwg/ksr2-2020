package utils;

import model.FuzzifyWeather;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGenerator {
    private List<FuzzifyWeather> weatherList;

    public ReportGenerator(List<FuzzifyWeather> weatherList) {
        this.weatherList = weatherList;
    }

    private Map<String, Map<String, Double>> getTerms() {
        List<String> variables = Arrays.asList("day_time", "cloudiness", "dampness", "wind_velocity",
                "precipitation_six", "snow", "pressure_station", "pressure_sea", "weather");

        Map<String, Map<String, Double>> fields = new HashMap<>();
        for (String var : variables) {
            Map<String, Integer> terms = new HashMap<>();
            for (FuzzifyWeather weather : weatherList)
                terms.put(weather.getField(var), terms.getOrDefault(weather.getField(var), 0) + 1);

            Map<String, Double> percentageTerms = new HashMap<>();
            for (Map.Entry<String, Integer> term : terms.entrySet())
                percentageTerms.put(term.getKey(), (term.getValue() / (double)weatherList.size()) );
            fields.put(var, percentageTerms);
        }

        return fields;
    }

    public void generateReport() {

        System.out.println(getTerms());
    }
}
