package Summaries.Multi;

import Summaries.Qualifier;
import Summaries.Quantifiers.IQuantifier;
import Summaries.Summarizer;
import enumerate.Season;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;

import java.util.List;


public class FormThree implements MultiSubjectLinguisticSummary {
    Season season1;
    Season season2;

    //cały set
    List<SimpleFuzzifyWeather> weatherList;
    Summarizer summarizer;
    // do quantifiera powinien trafić set z qualifiera, jesli natomiast brakuje qualifiera - caly set
    IQuantifier quantifier;
    Qualifier qualifier;
    FIS fis;

    public double t() {
        return 0;
    }

}
