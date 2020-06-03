package summaries.multi;

import enumerate.Season;
import lombok.AllArgsConstructor;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;
import summaries.Summarizer;
import summaries.quantifiers.Relative;

import java.util.List;

@AllArgsConstructor
public class FormOne implements MultiSubjectLinguisticSummary {
    Season season1;
    Season season2;

    //cały set
    List<SimpleFuzzifyWeather> weatherList;
    Summarizer summarizer;
    Relative quantifier;
    FIS fis;

    public double t1() {
        List<SimpleFuzzifyWeather> p1 = MultiSubjectLinguisticSummary.filterList(weatherList, season1);
        List<SimpleFuzzifyWeather> p2 = MultiSubjectLinguisticSummary.filterList(weatherList, season2);

        double sP1 = p1.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum() / p1.size();
        double sP2 = p2.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum() / p2.size();
        return quantifier.quantify(sP1 / (sP1 + sP2));
    }
}
