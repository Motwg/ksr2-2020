package Quantificators;

import Readers.FlcReader;
import lombok.Getter;
import lombok.Setter;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;
import utils.Summarizer;

import java.util.List;


@Getter
@Setter
public class Relative implements IQuantifier {

    private Summarizer summarizer;
    private List<SimpleFuzzifyWeather> weatherList;
    private final String inputFileName = "occ.fcl";
    private FIS fis;

    public Relative(Summarizer summarizer, List<SimpleFuzzifyWeather> weatherList) {
        this.summarizer = summarizer;
        this.weatherList = weatherList;
        this.fis = FlcReader.load(inputFileName);
    }

    public double t1(String term) {
        double sum = weatherList.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum() / weatherList.size();
        fis.setVariable("percentage", sum);
        return fis.getVariable("percentage").getMembership(term);
    }

    public double t6(String term) {
        if (Math.sqrt(1 - t1(term)) == 0)
            return 0.06;
        else
            return Math.sqrt(1 - t1(term));
    }

    public double t7(String term) {
        return Math.sqrt(0.8 * t6(term));
    }
}
