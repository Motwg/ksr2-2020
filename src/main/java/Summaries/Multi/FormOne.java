package Summaries.Multi;

import Summaries.Quantifiers.IQuantifier;
import Summaries.Summarizer;
import enumerate.Season;
import lombok.Builder;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;

import java.util.List;

@Builder
public class FormOne implements MultiSubjectLinguisticSummary {
    Season season1;
    Season season2;

    //cały set
    List<SimpleFuzzifyWeather> weatherList;
    Summarizer summarizer;
    IQuantifier quantifier;
    FIS fis;

    public double t() {
        return 0;
    }

}
