package Quantificators;

import lombok.Getter;
import lombok.Setter;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;
import utils.Summarizer;
import utils.Consts;
import utils.TermAnaliser;

import java.util.List;

@Getter
@Setter
public class Absolute implements IQuantificator {

    private Summarizer summarizer;
    private List<SimpleFuzzifyWeather> weatherList;
    private FIS fis;
    private TermAnaliser termAnaliser;
    private String term;

    public Absolute(Summarizer summarizer, List<SimpleFuzzifyWeather> weatherList, FIS fis, String term) {
        this.summarizer = summarizer;
        this.weatherList = weatherList;
        this.fis = fis;
        this.termAnaliser = new TermAnaliser(fis);
        this.term = term;
    }

    public double t1() {
        double sum = weatherList.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum();
        fis.setVariable(Consts.ABSOLUTE_VAR_NAME, sum);
        return fis.getVariable(Consts.ABSOLUTE_VAR_NAME).getMembership(term);
    }
}
