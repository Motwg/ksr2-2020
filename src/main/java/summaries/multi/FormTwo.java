package summaries.multi;

import enumerate.Season;
import lombok.AllArgsConstructor;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;
import summaries.Qualifier;
import summaries.Summarizer;
import summaries.quantifiers.Relative;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FormTwo implements MultiSubjectLinguisticSummary {
    Season season1;
    Season season2;

    //cały set
    List<SimpleFuzzifyWeather> weatherList;
    Summarizer summarizer;
    Relative quantifier;
    Qualifier qualifier;
    FIS fis;

    public double t1() {
        List<SimpleFuzzifyWeather> p1s2 = qualifier.qualify(weatherList).stream()
                .filter(weather -> weather.getSeason() == season1)
                .collect(Collectors.toList());
        List<SimpleFuzzifyWeather> p2 = weatherList.stream()
                .filter(weather -> weather.getSeason() == season2)
                .collect(Collectors.toList());
        double s1s2P1 = p1s2.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum() / p1s2.size();
        double s1P2 = p2.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum() / p2.size();
        return quantifier.quantify(s1s2P1 / (s1s2P1 + s1P2));
    }

}
