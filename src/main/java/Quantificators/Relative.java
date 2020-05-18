package Quantificators;

import Readers.FlcReader;
import lombok.Getter;
import lombok.Setter;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;
import utils.Consts;
import utils.Summarizer;
import utils.TermAnaliser;

import java.util.List;


@Getter
@Setter
public class Relative implements IQuantifier {

    private Summarizer summarizer;
    private List<SimpleFuzzifyWeather> weatherList;
    private FIS fis;
    private TermAnaliser termAnaliser;

    public Relative(Summarizer summarizer, List<SimpleFuzzifyWeather> weatherList, FIS fis) {
        this.summarizer = summarizer;
        this.weatherList = weatherList;
        this.fis = fis;
        this.termAnaliser = new TermAnaliser(fis);
    }

    public double t1(String term) {
        double sum = weatherList.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum() / weatherList.size();
        fis.setVariable(Consts.RELATIVE_VAR_NAME, sum);
        return fis.getVariable(Consts.RELATIVE_VAR_NAME).getMembership(term);
    }

    public double t6(String term) {
        return 1 - termAnaliser.countIn(term);
    }

    public double t7(String term) {
        return 1 - termAnaliser.countQSupp(term) / termAnaliser.countX(term);
    }
}
