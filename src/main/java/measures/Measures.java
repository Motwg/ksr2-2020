package measures;

import Quantificators.IQuantifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import model.SimpleFuzzifyWeather;
import utils.Qualifier;
import utils.Summarizer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public class Measures {

    //cały set
    List<SimpleFuzzifyWeather> weatherList;
    Summarizer summarizer;
    // do quantifiera powinien trafić set z qualifiera, jesli natomiast brakuje qualifiera - caly set
    IQuantifier quantifier;
    Qualifier qualifier;

    public double t1(String term) {
        if(quantifier == null)
            return weatherList.stream()
                    .mapToDouble(w -> summarizer.summarize(w).getValue())
                    .sum();
        else
            return quantifier.t1(term);
    }

    public double t2() {
        List<List<Integer>> ints;
        if(quantifier == null)
            ints = weatherList.stream()
                    .map(w -> summarizer.t4r(w))
                    .collect(Collectors.toList());
        else
            ints = quantifier.getWeatherList().stream()
                    .map(w -> summarizer.t4r(w))
                    .collect(Collectors.toList());

        List<Double> rList = new ArrayList<>();
        for (int i=0; i < summarizer.getTerms().size(); ++i) {
            double value = 0;
            for (List<Integer> summators : ints) {
                value += summators.get(i);
            }
            rList.add(value / (double)weatherList.size());
        }
        double value = 1;
        for(Double i : rList)
            value *= i;
        return 1 - Math.pow(value, 1.0 / (double) summarizer.getTerms().size());
    }

    public double t3() {
        if(quantifier == null)
            return weatherList.stream()
                    .mapToDouble(w -> summarizer.summarize(w).getValue())
                    .filter(value -> value > 0)
                    .map(value -> 1)
                    .sum() / weatherList.size();
        else
            return  quantifier.getWeatherList().stream()
                    .mapToDouble(w -> summarizer.summarize(w).getValue())
                    .filter(value -> value > 0)
                    .map(value -> 1)
                    .sum() / quantifier.getWeatherList().size();
    }

    public double t4() {
        List<List<Integer>> ints;
        if(quantifier == null)
             ints = weatherList.stream()
                    .map(w -> summarizer.t4r(w))
                    .collect(Collectors.toList());
        else
            ints = quantifier.getWeatherList().stream()
                    .map(w -> summarizer.t4r(w))
                    .collect(Collectors.toList());

        List<Double> rList = new ArrayList<>();
        for (int i=0; i < summarizer.getTerms().size(); ++i) {
            double value = 0;
            for (List<Integer> summators : ints) {
                value += summators.get(i);
            }
            rList.add(value / (double)quantifier.getWeatherList().size());
        }
        double value = 1;
        for(Double i : rList)
            value *= i;
        return 1 - Math.pow(value, 1.0 / (double) summarizer.getTerms().size());
    }

    public double t5() {
        return 2 * Math.pow(0.5, summarizer.getTerms().size());
    }

    public double t6(String term) {
        return quantifier.t6(term);
    }

    public double t7(String term) {
        return quantifier.t7(term);
    }

    public double t8() {
        List<List<Double>> values;
        if(quantifier == null)
            values = weatherList.stream()
                    .map(w -> summarizer.t2r(w))
                    .collect(Collectors.toList());
        else
            values = quantifier.getWeatherList().stream()
                    .map(w -> summarizer.t2r(w))
                    .collect(Collectors.toList());

        List<Double> rList = new ArrayList<>();
        for (int i=0; i < summarizer.getTerms().size(); ++i) {
            double value = 0;
            for (List<Double> summators : values) {
                value += summators.get(i);
            }
            rList.add(value / (double)quantifier.getWeatherList().size());
        }
        double value = 1;
        for(Double i : rList)
            value *= i;
        return 1 - Math.pow(value, 1.0 / (double) summarizer.getTerms().size());
    }

    public double t9() {
        return 1 - (double)quantifier.getWeatherList().size() / (double)weatherList.size();
    }

    public double t10() {
        final String term = qualifier.getLastTerm();
        return 1 - qualifier.getFullWeatherList().stream()
                .mapToDouble(w -> w.getTerm(term).getValue())
                .sum() / (double)weatherList.size();
    }

    public double t11() {
        // nasz kwalifikator zawsze składa się z jednego terma
        return 1;
    }
}
