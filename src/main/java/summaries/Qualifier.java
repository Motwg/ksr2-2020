package summaries;

import javafx.util.Pair;
import lombok.Getter;
import model.SimpleFuzzifyWeather;
import utils.AndOr;

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

    public Pair<Double, Double> qualify(List<SimpleFuzzifyWeather> weatherList, Summarizer summarizer) {
        double s1ands2 = weatherList.stream()
                .mapToDouble(weather -> AndOr.and(
                        summarizer.summarize(weather).getValue(),
                        weather.getTerm(term).getValue()
                )).sum();
        double s2 = weatherList.stream().mapToDouble(weather -> weather.getTerm(term).getValue()).sum();
        return new Pair<>(s1ands2, s2);
    }
}
