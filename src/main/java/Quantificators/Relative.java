package Quantificators;

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
public class Relative implements IQuantificator {

    private Summarizer summarizer;
    private List<SimpleFuzzifyWeather> weatherList;
    private FIS fis;
    private String term;

    public Relative(Summarizer summarizer, List<SimpleFuzzifyWeather> weatherList, FIS fis, String term) {
        this.summarizer = summarizer;
        this.weatherList = weatherList;
        this.fis = fis;
    }

    public double t1() {
        double sum = weatherList.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum() / weatherList.size();
        fis.setVariable(Consts.RELATIVE_VAR_NAME, sum);
        return fis.getVariable(Consts.RELATIVE_VAR_NAME).getMembership(term);
    }

}
