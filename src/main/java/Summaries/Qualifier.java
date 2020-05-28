package Summaries;

import lombok.Getter;
import model.SimpleFuzzifyWeather;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Qualifier {
    private String term;

    public Qualifier(String term) {
        this.term = term;
    }

    public List<SimpleFuzzifyWeather> qualify(List<SimpleFuzzifyWeather> fullWeatherList) {
        return fullWeatherList.stream()
                .filter(weather -> weather.getTerm(term).getValue() > 0)
                .collect(Collectors.toList());
    }

}
