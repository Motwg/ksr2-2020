package summaries;

import exception.QuantifierException;
import javafx.util.Pair;
import lombok.Builder;
import lombok.Getter;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;
import summaries.quantifiers.Absolute;
import summaries.quantifiers.IQuantifier;
import utils.TermAnalyser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public class LinguisticSummary {

    //cały set
    List<SimpleFuzzifyWeather> weatherList;
    Summarizer summarizer;
    // do quantifiera powinien trafić set z qualifiera, jesli natomiast brakuje qualifiera - caly set
    IQuantifier quantifier;
    @Getter
    Qualifier qualifier;
    FIS fis;

    public double t1() throws QuantifierException {
        if(qualifier == null) {
            double sum = weatherList.stream()
                    .mapToDouble(w -> summarizer.summarize(w).getValue())
                    .sum();

            if(quantifier == null)
                return sum / weatherList.size();
            else if (quantifier instanceof Absolute)
                return quantifier.quantify(sum);
            else
                return quantifier.quantify(sum / weatherList.size());
        } else {
            Pair<Double, Double> counterDenominator = qualifier.qualify(weatherList, summarizer);
            if(quantifier == null)
                return counterDenominator.getKey() / counterDenominator.getValue();
            else if (quantifier instanceof Absolute)
                throw new QuantifierException("Kwantyfikator bezwględny nie może zostać użyty w podsumowaniach w formie drugiej");
            else
                return quantifier.quantify(counterDenominator.getKey() / counterDenominator.getValue());
        }
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
        List<List<Integer>> ints;
        ints = weatherList.stream()
                .map(w -> summarizer.t4g(w))
                .collect(Collectors.toList());

        List<Double> rList = new ArrayList<>();
        for (int i=0; i < summarizer.getTerms().size(); ++i) {
            double value = 0;
            for (List<Integer> weatherG : ints)
                value += weatherG.get(i);
            rList.add(value / (double) weatherList.size());
        }
        //System.out.println("% przynależności do sumaryzatorów: " + rList);
        double value = rList.stream()
                .reduce(1.0, (x, y) -> x * y);
        return Math.abs(value - t3());
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
        return 1 - analyser.countClm(quantifier.getTerm());
    }

    public double t8() {
        TermAnalyser analyser = new TermAnalyser(fis);
        double value = summarizer.getTerms().stream()
                .mapToDouble(term -> analyser.countClm(term))
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
        return 1 - analyser.countClm(term);
    }

    public double t11() {
        // nasz kwalifikator zawsze składa się z jednego terma
        return 1;
    }

    public double overallT(List<Double> weights) throws QuantifierException {
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
