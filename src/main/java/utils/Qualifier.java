package utils;

import model.SimpleFuzzifyWeather;

import java.util.List;
import java.util.stream.Collectors;

public class Qualifier {
    private List<SimpleFuzzifyWeather> fullWeatherList;

    public Qualifier(List<SimpleFuzzifyWeather> fullWeatherList) {
        this.fullWeatherList = fullWeatherList;
    }

    public List<SimpleFuzzifyWeather> qualify(String term) {
        return fullWeatherList.stream()
                .filter(weather -> weather.getTerm(term).getValue() > 0)
                .collect(Collectors.toList());
    }
}
