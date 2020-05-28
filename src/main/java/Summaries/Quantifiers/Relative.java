package Summaries.Quantifiers;

import lombok.Getter;
import lombok.Setter;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;
import utils.Constants;
import Summaries.Summarizer;

import java.util.List;


@Getter
@Setter
public class Relative implements IQuantifier {

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
        fis.setVariable(Constants.RELATIVE_VAR_NAME, sum);
        return fis.getVariable(Constants.RELATIVE_VAR_NAME).getMembership(term);
    }

}
