import model.Weather;
import utils.DataReader;

import java.util.List;

public class App {

    private static final String FIRST_FILENAME = "2016.csv";
    private static final String SECOND_FILENAME = "2017.csv";

    public static void main(String[] args) {
        List<Weather> weatherData = DataReader.readWeatherFromCsv(FIRST_FILENAME);
        weatherData.addAll(DataReader.readWeatherFromCsv(SECOND_FILENAME));
        //Only to demonstrate:
        weatherData.forEach(w -> System.out.println(w.toString()));
    }
}
