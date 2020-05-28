package Summaries.Quantifiers;

import lombok.Getter;
import lombok.Setter;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;
import Summaries.Summarizer;
import utils.Constants;
import utils.TermAnalyser;

import java.util.List;

@Getter
@Setter
public class Absolute implements IQuantifier {

    private Summarizer summarizer;
    private List<SimpleFuzzifyWeather> weatherList;
    private FIS fis;
    private TermAnalyser termAnalyser;
    private String term;

    public Absolute(Summarizer summarizer, List<SimpleFuzzifyWeather> weatherList, FIS fis, String term) {
        this.summarizer = summarizer;
        this.weatherList = weatherList;
        this.fis = fis;
        this.termAnalyser = new TermAnalyser(fis);
        this.term = term;
    }

    public double t1() {
        double sum = weatherList.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum();
        fis.setVariable(Constants.ABSOLUTE_VAR_NAME, sum);
        return fis.getVariable(Constants.ABSOLUTE_VAR_NAME).getMembership(term);
    }
}
