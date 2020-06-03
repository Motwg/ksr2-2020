package summaries.multi;

import enumerate.Season;
import lombok.AllArgsConstructor;
import model.SimpleFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;
import summaries.Summarizer;

import java.util.List;

@AllArgsConstructor
public class FormFour implements MultiSubjectLinguisticSummary {
    Season season1;
    Season season2;

    //cały set
    List<SimpleFuzzifyWeather> weatherList;
    Summarizer summarizer;
    FIS fis;

    public double t1() {
        List<SimpleFuzzifyWeather> p1 = MultiSubjectLinguisticSummary.filterList(weatherList, season1);
        List<SimpleFuzzifyWeather> p2 = MultiSubjectLinguisticSummary.filterList(weatherList, season2);
        double sP1 = p1.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum();
        double sP2 = p2.stream()
                .mapToDouble(w -> summarizer.summarize(w).getValue())
                .sum();
        return sP1 / (sP1 + sP2);
    }
}
