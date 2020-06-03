package summaries.multi;

import enumerate.Season;
import model.SimpleFuzzifyWeather;

import java.util.List;
import java.util.stream.Collectors;

public interface MultiSubjectLinguisticSummary {
    double t1();
    static List<SimpleFuzzifyWeather> filterList(List<SimpleFuzzifyWeather> weatherList, Season season) {
        return weatherList.stream()
                .filter(weather -> weather.getSeason() == season)
                .collect(Collectors.toList());
    };
}
