package Summaries;

import enumerate.Operator;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import model.SimpleFuzzifyWeather;
import utils.AndOr;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class Summarizer {
    private List<String> terms;
    private Operator operator;

    public Summarizer(String term) {
        this.terms = Arrays.asList(term);
    }

    public Pair<String, Double> summarize(SimpleFuzzifyWeather sfw) {
        if(terms.size() == 1) {
            return sfw.getTerm(terms.get(0));
        } else {
            if (operator == Operator.or)
                return AndOr.or(terms.stream()
                        .map(term -> sfw.getTerm(term))
                        .collect(Collectors.toList()));
            else
                return AndOr.and(terms.stream()
                        .map(term -> sfw.getTerm(term))
                        .collect(Collectors.toList()));
        }
    }

    public List<Integer> t4r(SimpleFuzzifyWeather sfw) {
        return terms.stream()
                .map(term -> sfw.getTerm(term).getValue())
                .map(value -> {
                    if (value > 0)
                        return 1;
                    else
                        return 0;
                })
                .collect(Collectors.toList());
    }

}
