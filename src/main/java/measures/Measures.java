package measures;

import Quantificators.IQuantificator;
import lombok.Builder;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;
import utils.Qualifier;
import utils.Summarizer;
import utils.TermAnaliser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public class Measures {

    //cały set
    List<SimpleFuzzifyWeather> weatherList;
    Summarizer summarizer;
    // do quantifiera powinien trafić set z qualifiera, jesli natomiast brakuje qualifiera - caly set
    IQuantificator quantificator;
    Qualifier qualifier;
    FIS fis;
    TermAnaliser termAnaliser;

    public double t1() {
        if(quantificator == null)
            return weatherList.stream()
                    .mapToDouble(w -> summarizer.summarize(w).getValue())
                    .sum();
        else
            return quantificator.t1();
    }

    public double t2() {
        TermAnaliser analyser = new TermAnaliser(fis);
        double value = summarizer.getTerms().stream()
                .mapToDouble(analyser::countIn)
                .reduce(1, (a, b) -> a * b);
        return 1 - Math.pow(value, 1.0 / summarizer.getTerms().size());
    }

    public double t3() {
        if(quantificator == null)
            return weatherList.stream()
                    .mapToDouble(w -> summarizer.summarize(w).getValue())
                    .filter(value -> value > 0)
                    .map(value -> 1)
                    .sum() / weatherList.size();
        else
            return  quantificator.getWeatherList().stream()
                    .mapToDouble(w -> summarizer.summarize(w).getValue())
                    .filter(value -> value > 0)
                    .map(value -> 1)
                    .sum() / quantificator.getWeatherList().size();
    }

    public double t4() {
        List<List<Integer>> ints;
        if(quantificator == null)
             ints = weatherList.stream()
                    .map(w -> summarizer.t4r(w))
                    .collect(Collectors.toList());
        else
            ints = quantificator.getWeatherList().stream()
                    .map(w -> summarizer.t4r(w))
                    .collect(Collectors.toList());

        List<Double> rList = new ArrayList<>();
        for (int i=0; i < summarizer.getTerms().size(); ++i) {
            double value = 0;
            for (List<Integer> summators : ints) {
                value += summators.get(i);
            }
            rList.add(value / (double) quantificator.getWeatherList().size());
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
        return 1 - termAnaliser.countIn(quantificator.getTerm());
    }

    public double t7() {
        return 1 - termAnaliser.countQSupp(quantificator.getTerm()) / termAnaliser.countX(quantificator.getTerm());
    }

    public double t8() {
        TermAnaliser analyser = new TermAnaliser(fis);
        double value = summarizer.getTerms().stream()
                .mapToDouble(term -> analyser.countQSupp(term) / analyser.countX(term))
                .reduce(1, (a, b) -> a * b);
        return 1 - Math.pow(value, 1.0 / summarizer.getTerms().size());
    }

    public double t9() {
        TermAnaliser analyser = new TermAnaliser(fis);
        return 1 - analyser.countIn(qualifier.getLastTerm());
    }

    public double t10() {
        TermAnaliser analyser = new TermAnaliser(fis);
        final String term = qualifier.getLastTerm();
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
