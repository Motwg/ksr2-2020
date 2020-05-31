package summaries.multi;

import summaries.Qualifier;
import summaries.quantifiers.Relative;
import summaries.Summarizer;
import enumerate.Season;
import lombok.AllArgsConstructor;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;

import java.util.List;
import java.util.stream.Collectors;

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

    public double t() {
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
        return quantifier.quantify(s1s2P2 / (s1P1 + s1s2P2));
    }

}
