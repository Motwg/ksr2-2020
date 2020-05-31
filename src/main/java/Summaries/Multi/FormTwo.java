package Summaries.Multi;

import Summaries.Qualifier;
import Summaries.Quantifiers.Relative;
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
public class FormTwo implements MultiSubjectLinguisticSummary {
    Season season1;
    Season season2;

    //cały set
    List<SimpleFuzzifyWeather> weatherList;
    Summarizer summarizer;
    Relative quantifier;
    Qualifier qualifier;
    FIS fis;

    public double t1() {
        List<SimpleFuzzifyWeather> p1s2 = qualifier.qualify(weatherList).stream()
                .filter(weather -> weather.getSeason() == season1)
                .collect(Collectors.toList());
        List<SimpleFuzzifyWeather> p2 = weatherList.stream()
                .filter(weather -> weather.getSeason() == season2)
                .collect(Collectors.toList());
        double s1s2P1 = p1s2.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum() / p1s2.size();
        double s1P2 = p2.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum() / p2.size();
        return quantifier.quantify(s1s2P1 / (s1s2P1 + s1P2));
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
        if(qualifier != null)
            newWeatherList = qualifier.qualify(weatherList);

        return newWeatherList.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .filter(value -> value > 0)
                .map(value -> 1)
                .sum() / newWeatherList.size();
    }

    public double t4() {
        List<SimpleFuzzifyWeather> newWeatherList = weatherList;
        if(qualifier != null)
            newWeatherList = qualifier.qualify(weatherList);

        List<List<Integer>> ints;
        if(quantifier == null)
            ints = weatherList.stream()
                    .map(w -> summarizer.t4r(w))
                    .collect(Collectors.toList());
        else
            ints = newWeatherList.stream()
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

    public double t6() {
        TermAnalyser analyser = new TermAnalyser(fis);
        return 1 - analyser.countIn(quantifier.getTerm());
    }

    public double t7() {
        TermAnalyser analyser = new TermAnalyser(fis);
        return 1 - analyser.countQSupp(quantifier.getTerm()) / analyser.countX(quantifier.getTerm());
    }

    public double t8() {
        TermAnalyser analyser = new TermAnalyser(fis);
        double value = summarizer.getTerms().stream()
                .mapToDouble(term -> analyser.countQSupp(term) / analyser.countX(term))
                .reduce(1, (a, b) -> a * b);
        return 1 - Math.pow(value, 1.0 / summarizer.getTerms().size());
    }

    public double t9() {
        TermAnalyser analyser = new TermAnalyser(fis);
        return 1 - analyser.countIn(qualifier.getTerm());
    }

    public double t10() {
        TermAnalyser analyser = new TermAnalyser(fis);
        final String term = qualifier.getTerm();
        return 1 - analyser.countQSupp(term) / analyser.countX(term);
    }

    public double t11() {
        // nasz kwalifikator zawsze składa się z jednego terma
        return 1;
    }

    public double overallT(List<Double> weights) {
        if (weights.size() != 11) {
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
                    t6() * weights.get(5),
                    t7() * weights.get(6),
                    t8() * weights.get(7),
                    t9() * weights.get(8),
                    t10() * weights.get(9),
                    t11() * weights.get(10)
            );
            return values.stream().mapToDouble(a->a).max().orElse(0.0);
        }
    }
}
