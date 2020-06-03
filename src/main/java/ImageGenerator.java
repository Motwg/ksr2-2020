import model.Weather;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import readers.DataReader;
import readers.FlcReader;
import utils.Constants;

import java.util.List;

public class ImageGenerator {

    private static final String FIRST_FILENAME = "2016.csv";
    private static final String SECOND_FILENAME = "2017.csv";

    public static void main(String[] args) throws Exception {
        List<Weather> weatherData = DataReader.readWeatherFromCsv(FIRST_FILENAME);
        weatherData.addAll(DataReader.readWeatherFromCsv(SECOND_FILENAME));
        FIS fis = FlcReader.load(Constants.INPUT_FCL_NAME);

        fis.getVariable("dampness").setUniverseMax(100);
        fis.getVariable("dampness").setUniverseMin(0);
        fis.getVariable("day_time").setUniverseMin(0);
        fis.getVariable("day_time").setUniverseMax(24);
        fis.getVariable("temperature_spring").setUniverseMin(-15);
        fis.getVariable("temperature_spring").setUniverseMax(35);
        fis.getVariable("temperature_wet_spring").setUniverseMin(-25);
        fis.getVariable("temperature_wet_spring").setUniverseMax(25);
        fis.getVariable("cloudiness").setUniverseMin(0);
        fis.getVariable("cloudiness").setUniverseMax(10);
        fis.getVariable("wind_velocity").setUniverseMin(0);
        fis.getVariable("wind_velocity").setUniverseMax(50);
        fis.getVariable("precipitation_six").setUniverseMin(0);
        fis.getVariable("precipitation_six").setUniverseMax(8);
        fis.getVariable("pressure_station").setUniverseMin(799);
        fis.getVariable("pressure_station").setUniverseMax(870);
        fis.getVariable("pressure_sea").setUniverseMin(980);
        fis.getVariable("pressure_sea").setUniverseMax(1050);
        fis.getVariable("snow").setUniverseMin(0);
        fis.getVariable("snow").setUniverseMax(15);

        fis.getVariable("percentage").setUniverseMin(0);
        fis.getVariable("percentage").setUniverseMax(1);
        fis.getVariable("absolute").setUniverseMin(0);
        fis.getVariable("absolute").setUniverseMax(17544);

        JFuzzyChart.get().chart(fis);
    }
}
