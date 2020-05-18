import Quantificators.Absolute;
import Quantificators.Relative;
import enumerate.Operator;
import measures.Measures;
import model.SimpleFuzzifyWeather;
import model.Weather;
import net.sourceforge.jFuzzyLogic.FIS;
import Readers.DataReader;
import Readers.FlcReader;
import utils.Consts;
import utils.Qualifier;
import utils.Summarizer;
import utils.TermAnaliser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    private static final String FIRST_FILENAME = "2016.csv";
    private static final String SECOND_FILENAME = "2017.csv";

    public static void main(String[] args) throws Exception {
        List<Weather> weatherData = DataReader.readWeatherFromCsv(FIRST_FILENAME);
        weatherData.addAll(DataReader.readWeatherFromCsv(SECOND_FILENAME));
        FIS fis = FlcReader.load(Consts.INPUT_FCL_NAME);

        List<SimpleFuzzifyWeather> fWeatherData = weatherData.stream()
                .map(w -> w.fuzzify(fis))
                .collect(Collectors.toList());

        Qualifier qualifier = new Qualifier(fWeatherData);
        List<SimpleFuzzifyWeather> night = qualifier.qualify("night");

        Summarizer summarizer = new Summarizer(Arrays.asList("freezing_temperature", "optimal_dampness"), Operator.and);
        Absolute absolute = new Absolute(summarizer, night, fis);
        Relative relative = new Relative(summarizer, night, fis); // jesli jest quantifier, ograniczony set! (night)
                                                            // jesli nie ma, caly (fWeatherData)

        //Quantifier(X)             Qualifier(nocą)  Summarizer(bardzo zimno, ...)
        //w X pomiarów przeprowadzonych nocą było bardzo zimno //i ... lub ...
        Measures measures = Measures.builder()
                .weatherList(fWeatherData) //zawsze cały set
                .summarizer(summarizer)
                .qualifier(qualifier)
                .quantifier(relative)// w tym panu powinien byc set wygenerowany przez qualifier
                .fis(fis)
                .build();
        System.out.println(absolute.t1("more_than_1000"));
        System.out.println("t1: " + measures.t1("about_half"));
        System.out.println("t2: " + measures.t2());
        System.out.println("t3: " + measures.t3());
        System.out.println("t4: " + measures.t4());
        System.out.println("t5: " + measures.t5());
        System.out.println("t6: " + measures.t6("about_half"));
        System.out.println("t7: " + measures.t7("about_half"));
        System.out.println("t8: " + measures.t8());
        System.out.println("t9: " + measures.t9());
        System.out.println("t10: " + measures.t10());
        System.out.println("t11: " + measures.t11());
    }

}
