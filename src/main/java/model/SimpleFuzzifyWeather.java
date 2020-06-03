package model;

import enumerate.Season;
import exception.FuzzifyException;
import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class SimpleFuzzifyWeather {
    private List<Pair<String, Double>> weather = new ArrayList<>();
    private Season season;

    public void addTerms(List<Pair<String, Double>> terms) {
        weather.addAll(terms);
    }

    public Pair<String, Double> getTerm(String term) {
        try {
            return weather.stream()
                    .filter(t -> t.getKey().compareTo(term) == 0)
                    .findAny()
                    .orElseThrow(() -> new FuzzifyException("Term " + term + " does not exist, creating new one"));
        } catch (FuzzifyException e) {
            return new Pair<>(term, 0.0);
        }
    }
}
