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
public class Absolute implements IQuantifier {

    private Summarizer summarizer;
    private List<SimpleFuzzifyWeather> weatherList;
    private final String inputFileName = "occ.fcl";
    private FIS fis;

    public Absolute(Summarizer summarizer, List<SimpleFuzzifyWeather> weatherList) {
        this.summarizer = summarizer;
        this.weatherList = weatherList;
        this.fis = FlcReader.load(inputFileName);
    }

    public double t1(String term) {
        double sum = weatherList.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum();
        fis.setVariable("absolute", sum);
        return fis.getVariable("absolute").getMembership(term);
    }
}
