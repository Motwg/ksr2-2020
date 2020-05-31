package Summaries.Multi;

import Summaries.Summarizer;
import enumerate.Season;
import lombok.AllArgsConstructor;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;
import utils.TermAnalyser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FormFour implements MultiSubjectLinguisticSummary {
    Season season1;
    Season season2;

    //cały set
    List<SimpleFuzzifyWeather> weatherList;
    Summarizer summarizer;
    FIS fis;

    public double t1() {
        List<SimpleFuzzifyWeather> p1 = weatherList.stream()
                .filter(weather -> weather.getSeason() == season1)
                .collect(Collectors.toList());
        List<SimpleFuzzifyWeather> p2 = weatherList.stream()
                .filter(weather -> weather.getSeason() == season2)
                .collect(Collectors.toList());
        double sP1 = p1.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum();
        double sP2 = p2.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum();
        return sP1 / (sP1 + sP2);
    }

    public double t2() {
        TermAnalyser analyser = new TermAnalyser(fis);
        double value = summarizer.getTerms().stream()
                .mapToDouble(analyser::countIn)
                .reduce(1, (a, b) -> a * b);
        return 1 - Math.pow(value, 1.0 / summarizer.getTerms().size());
    }

    public double t3() {
        List<SimpleFuzzifyWeather> newWeatherList = weatherList;

        return newWeatherList.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .filter(value -> value > 0)
                .map(value -> 1)
                .sum() / newWeatherList.size();
    }

    public double t4() {
        List<SimpleFuzzifyWeather> newWeatherList = weatherList;

        List<List<Integer>> ints = newWeatherList.stream()
                    .map(w -> summarizer.t4r(w))
                    .collect(Collectors.toList());

        List<Double> rList = new ArrayList<>();
        for (int i=0; i < summarizer.getTerms().size(); ++i) {
            double value = 0;
            for (List<Integer> summators : ints) {
                value += summators.get(i);
            }
            rList.add(value / (double) newWeatherList.size());
        }
        double value = 1;
        for(Double i : rList)
            value *= i;
        return 1 - Math.pow(value, 1.0 / (double) summarizer.getTerms().size());
    }

    public double t5() {
        return 2 * Math.pow(0.5, summarizer.getTerms().size());
    }

    public double t8() {
        TermAnalyser analyser = new TermAnalyser(fis);
        double value = summarizer.getTerms().stream()
                .mapToDouble(term -> analyser.countQSupp(term) / analyser.countX(term))
                .reduce(1, (a, b) -> a * b);
        return 1 - Math.pow(value, 1.0 / summarizer.getTerms().size());
    }

    public double overallT(List<Double> weights) {
        if (weights.size() != 6) {
            throw new RuntimeException("Nieprawidłowa ilość wag");
        } else if(0.99 > weights.stream().mapToDouble(a -> a).sum() ||
                weights.stream().mapToDouble(a -> a).sum() > 1.01)
            throw new RuntimeException("Suma wag jest różna od 1");
        else {
            List<Double> values = Arrays.asList(
                    t1() * weights.get(0),
                    t2() * weights.get(1),
                    t3() * weights.get(2),
                    t4() * weights.get(3),
                    t5() * weights.get(4),
                    t8() * weights.get(7)
            );
            return values.stream().mapToDouble(a->a).max().orElse(0.0);
        }
    }
}
