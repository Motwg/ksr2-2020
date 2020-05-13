import enumerate.Season;
import model.FuzzifyWeather;
import model.Weather;
import net.sourceforge.jFuzzyLogic.FIS;
import utils.DataReader;
import utils.FlcReader;
import utils.ReportGenerator;

import java.util.List;
import java.util.stream.Collectors;

public class App {

    private static final String FIRST_FILENAME = "2016.csv";
    private static final String SECOND_FILENAME = "2017.csv";
    private static final double activation = .0;

    public static void main(String[] args) throws Exception {
        List<Weather> weatherData = DataReader.readWeatherFromCsv(FIRST_FILENAME);
        weatherData.addAll(DataReader.readWeatherFromCsv(SECOND_FILENAME));
        FIS fis = FlcReader.load("weather.fcl");

        List<FuzzifyWeather> fWeatherData = weatherData.stream()
                //.limit(15)
                .map(weather -> weather.fuzzify(fis, activation))
                .collect(Collectors.toList());
        List<FuzzifyWeather> filteredData = fWeatherData.stream()
                .filter(weather -> weather.getSeason() == Season.Winter)
                //.filter(weather -> weather.getDayTime().compareTo("morning") == 0)
                .collect(Collectors.toList());

        ReportGenerator generator = new ReportGenerator(filteredData);
        generator.generateReport1();
    }


}
