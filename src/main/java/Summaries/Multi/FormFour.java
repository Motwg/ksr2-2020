package Summaries.Multi;

import Summaries.Summarizer;
import enumerate.Season;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;

import java.util.List;

public class FormFour implements MultiSubjectLinguisticSummary {
    Season season1;
    Season season2;

    //cały set
    List<SimpleFuzzifyWeather> weatherList;
    Summarizer summarizer;
    FIS fis;

    public double t() {
        return 0;
    }

}
