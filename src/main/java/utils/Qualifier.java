package utils;

import lombok.Getter;
import model.SimpleFuzzifyWeather;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Qualifier {
    private List<SimpleFuzzifyWeather> fullWeatherList;
    private String lastTerm;

    public Qualifier(List<SimpleFuzzifyWeather> fullWeatherList) {
        this.fullWeatherList = fullWeatherList;
    }

    public List<SimpleFuzzifyWeather> qualify(String term) {
        lastTerm = term;
        return fullWeatherList.stream()
                .filter(weather -> weather.getTerm(term).getValue() > 0)
                .collect(Collectors.toList());
    }

}
