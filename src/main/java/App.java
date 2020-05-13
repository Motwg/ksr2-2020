import model.FuzzifyWeather;
import model.Weather;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import utils.DataReader;

import java.io.File;
import java.util.List;

public class App {

    private static final String FIRST_FILENAME = "2016.csv";
    private static final String SECOND_FILENAME = "2017.csv";

    public static void main(String[] args) throws Exception {
        List<Weather> weatherData = DataReader.readWeatherFromCsv(FIRST_FILENAME);
        weatherData.addAll(DataReader.readWeatherFromCsv(SECOND_FILENAME));
        FIS fis = load("weather.fcl");
        System.out.println(weatherData.get(8));
        FuzzifyWeather fWeather = weatherData.get(8).fuzzify(fis, 0.5);
        System.out.println(fWeather);
        //fis.evaluate();
        //Variable dampness = fis.getVariable("dampness");

        //System.out.println(dampness);
        //JFuzzyChart.get().chart(dampness, dampness.getDefuzzifier(), true);

        //Only to demonstrate:
        //weatherData.forEach(w -> System.out.println(w.toString()));

    }

    public static FIS load(String filename) throws Exception {
        // Load from 'FCL' file
        String fileName = "data" + File.separator + filename;
        FIS fis = FIS.load(fileName,true);
        // Error while loading?
        if( fis == null ) {
            System.err.println("Can't load file: '"
                    + fileName + "'");
            throw new Exception();
        }
        //JFuzzyChart.get().chart(fis);
        return fis;


        // Show


        //fis.chart();

        /*
        // Set inputs
        fis.setVariable("service", 3);
        fis.setVariable("food", 7);

        // Evaluate
        fis.evaluate();

        // Show output variable's chart
        //fis.getVariable("tip").chartDefuzzifier(true);
        Variable tip = fis.getVariable("tip");
        JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);

        // Print ruleSet
        System.out.println(fis);
        */
    }
}
