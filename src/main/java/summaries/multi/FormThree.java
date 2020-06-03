package summaries.multi;

import enumerate.Season;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;
import summaries.Qualifier;
import summaries.Summarizer;
import summaries.quantifiers.Relative;

import java.util.List;

@AllArgsConstructor
public class FormThree implements MultiSubjectLinguisticSummary {
    Season season1;
    Season season2;

    //cały set
    List<SimpleFuzzifyWeather> weatherList;
    Summarizer summarizer;
    Relative quantifier;
    Qualifier qualifier;
    FIS fis;

    public double t1() {
        List<SimpleFuzzifyWeather> p1 = MultiSubjectLinguisticSummary.filterList(weatherList, season1);
        List<SimpleFuzzifyWeather> p2 = MultiSubjectLinguisticSummary.filterList(weatherList, season2);
        Pair<Double, Double> s1Ands2Overs2 = qualifier.qualify(p1, summarizer);
        double s1p2 = p2.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum();
        double s1p1 = p1.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum();

        return quantifier.quantify((s1Ands2Overs2.getKey() / p1.size())
                /
                (s1p1 / p1.size() + s1p2 / p2.size()));
        /*
        List<SimpleFuzzifyWeather> p1 = weatherList.stream()
                .filter(weather -> weather.getSeason() == season1)
                .collect(Collectors.toList());
        List<SimpleFuzzifyWeather> p2s2 = qualifier.qualify(weatherList).stream()
                .filter(weather -> weather.getSeason() == season2)
                .collect(Collectors.toList());
        double s1P1 = p1.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum() / p1.size();
        double s1s2P2 = p2s2.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum() / p2s2.size();
        return quantifier.quantify(s1s2P2 / (s1P1 + s1s2P2));*/
    }
}
