import readers.DataReader;
import readers.FlcReader;
import summaries.LinguisticSummary;
import summaries.multi.FormOne;
import summaries.Qualifier;
import summaries.quantifiers.Absolute;
import summaries.quantifiers.Relative;
import summaries.Summarizer;
import enumerate.Operator;
import enumerate.Season;
import model.SimpleFuzzifyWeather;
import model.Weather;
import net.sourceforge.jFuzzyLogic.FIS;
import utils.Constants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    private static final String FIRST_FILENAME = "2016.csv";
    private static final String SECOND_FILENAME = "2017.csv";

    public static void main(String[] args) throws Exception {
        List<Weather> weatherData = DataReader.readWeatherFromCsv(FIRST_FILENAME);
        weatherData.addAll(DataReader.readWeatherFromCsv(SECOND_FILENAME));
        FIS fis = FlcReader.load(Constants.INPUT_FCL_NAME);

        List<SimpleFuzzifyWeather> fWeatherData = weatherData.stream()
                .map(w -> w.fuzzify(fis))
                .collect(Collectors.toList());

        Qualifier qualifier = new Qualifier("night");
        List<SimpleFuzzifyWeather> night = qualifier.qualify(fWeatherData);

        Summarizer summarizer = new Summarizer(Arrays.asList("cool_temperature"), Operator.and);
        Absolute absolute = new Absolute(fis, "more_than_1000");
        Relative relative = new Relative(fis, "about_half");

        //Quantifier(X)             Qualifier(nocą)  Summarizer(bardzo zimno, ...)
        //w X pomiarów przeprowadzonych nocą było bardzo zimno //i ... lub ...
        LinguisticSummary linguisticSummary = LinguisticSummary.builder()
                .weatherList(fWeatherData) //zawsze cały set
                .summarizer(summarizer)
                .qualifier(qualifier)
                .quantifier(relative)
                .fis(fis)
                .build();
        System.out.println("t1: " + linguisticSummary.t1());
        System.out.println("t2: " + linguisticSummary.t2());
        System.out.println("t3: " + linguisticSummary.t3());
        System.out.println("t4: " + linguisticSummary.t4());
        System.out.println("t5: " + linguisticSummary.t5());
        System.out.println("t6: " + linguisticSummary.t6());
        System.out.println("t7: " + linguisticSummary.t7());
        System.out.println("t8: " + linguisticSummary.t8());
        System.out.println("t9: " + linguisticSummary.t9());
        System.out.println("t10: " + linguisticSummary.t10());
        System.out.println("t11: " + linguisticSummary.t11());

        FormOne ponadpolowawiosnawodniesieniudozimyjestchlodna =
                new FormOne(Season.Spring, Season.Winter, fWeatherData, summarizer, relative, fis);
        System.out.println(ponadpolowawiosnawodniesieniudozimyjestchlodna.t1());
    }

}
