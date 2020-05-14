import Quantificators.Absolute;
import Quantificators.Relative;
import enumerate.Operator;
import measures.Measures;
import model.SimpleFuzzifyWeather;
import model.Weather;
import net.sourceforge.jFuzzyLogic.FIS;
import Readers.DataReader;
import Readers.FlcReader;
import utils.Qualifier;
import utils.Summarizer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    private static final String FIRST_FILENAME = "2016.csv";
    private static final String SECOND_FILENAME = "2017.csv";

    public static void main(String[] args) throws Exception {
        List<Weather> weatherData = DataReader.readWeatherFromCsv(FIRST_FILENAME);
        weatherData.addAll(DataReader.readWeatherFromCsv(SECOND_FILENAME));
        FIS fis = FlcReader.load("weather.fcl");

        List<SimpleFuzzifyWeather> fWeatherData = weatherData.stream()
                .map(w -> w.fuzzify(fis))
                .collect(Collectors.toList());

        Qualifier qualifier = new Qualifier(fWeatherData);
        List<SimpleFuzzifyWeather> night = qualifier.qualify("night");

        Summarizer summarizer = new Summarizer(Arrays.asList("freezing_temperature"), Operator.or);
        Absolute absolute = new Absolute(summarizer, night);
        Relative relative = new Relative(summarizer, night); // jesli jest quantifier, ograniczony set! (night)
                                                            // jesli nie ma, caly (fWeatherData)

        //Quantifier(X)             Qualifier(nocą)  Summarizer(bardzo zimno, ...)
        //w X pomiarów przeprowadzonych nocą było bardzo zimno //i ... lub ...
        Measures measures = Measures.builder()
                .weatherList(fWeatherData) //zawsze cały set
                .summarizer(summarizer)
                .qualifier(qualifier)
                .quantifier(relative)// w tym panu powinien byc set wygenerowany przez qualifier
                .build();
        System.out.println(absolute.t1("more_than_1000"));
        System.out.println(measures.t1("about_half"));
        System.out.println(measures.t3());
        System.out.println(measures.t4());
        System.out.println(measures.t5());


        /*
        List<FuzzifyWeather> fWeatherData = weatherData.stream()
                //.limit(15)
                .map(weather -> weather.fuzzify(fis, activation))
                .collect(Collectors.toList());
        List<FuzzifyWeather> filteredData = fWeatherData.stream()
                .filter(weather -> weather.getSeason() == Season.Winter)
                //.filter(weather -> weather.getDayTime().compareTo("morning") == 0)
                .collect(Collectors.toList());
         */

        //ReportGenerator generator = new ReportGenerator(filteredData);
        //generator.generateReport();
    }


}
