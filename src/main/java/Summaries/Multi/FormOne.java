package Summaries.Multi;

import Summaries.Qualifier;
import Summaries.Quantifiers.IQuantifier;
import Summaries.Summarizer;
import enumerate.Season;
import lombok.Builder;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;
import utils.TermAnalyser;

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
    TermAnalyser termAnalyser;

    public double t() {
        return 0;
    }

}
