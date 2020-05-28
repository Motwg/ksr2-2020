package Summaries.Multi;

import Summaries.Quantifiers.IQuantifier;
import Summaries.Summarizer;
import enumerate.Season;
import lombok.Builder;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;

import java.util.List;
import java.util.stream.Collectors;

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
        List<SimpleFuzzifyWeather> p1 = weatherList.stream()
                .filter(weather -> weather.getSeason() == season1)
                .collect(Collectors.toList());
        List<SimpleFuzzifyWeather> p2 = weatherList.stream()
                .filter(weather -> weather.getSeason() == season2)
                .collect(Collectors.toList());
        double sP1 = p1.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum() / p1.size();
        double sP2 = p2.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum() / p2.size();
        return 0;
    }

}
