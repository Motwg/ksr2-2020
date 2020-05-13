import model.FuzzifyWeather;
import model.Weather;
import net.sourceforge.jFuzzyLogic.FIS;
import utils.DataReader;
import utils.ReportGenerator;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    private static final String FIRST_FILENAME = "2016.csv";
    private static final String SECOND_FILENAME = "2017.csv";
    private static final double activation = .3;

    public static void main(String[] args) throws Exception {
        List<Weather> weatherData = DataReader.readWeatherFromCsv(FIRST_FILENAME);
        weatherData.addAll(DataReader.readWeatherFromCsv(SECOND_FILENAME));
        FIS fis = load("weather.fcl");

        List<FuzzifyWeather> fWeaterData = weatherData.stream()
                //.limit(15)
                .map(weather -> weather.fuzzify(fis, activation))
                .collect(Collectors.toList());

        ReportGenerator generator = new ReportGenerator(fWeaterData);
        generator.generateReport();
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
