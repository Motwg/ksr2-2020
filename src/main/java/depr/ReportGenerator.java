package depr;
/*
import javafx.util.Pair;
import depr.DeprFuzzifyWeather;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.util.*;

public class ReportGenerator {
    private List<DeprFuzzifyWeather> weatherList;
    private final String inputFileName = "occ.fcl";

    public ReportGenerator(List<DeprFuzzifyWeather> weatherList) {
        this.weatherList = weatherList;
    }

    private Map<String, String> getTerms(String var) {


        FIS fis = FlcReader.load(inputFileName);
        //Map<String, Map<String, String>> fields = new HashMap<>();

        //for (String var : variables) {
            // occurrences
            Map<String, Integer> terms = new HashMap<>();
            for (DeprFuzzifyWeather weather : weatherList)
                terms.put(weather.getField(var), terms.getOrDefault(weather.getField(var), 0) + 1);

            // change to percentage
            Map<String, Double> percentageTerms = new HashMap<>();
            for (Map.Entry<String, Integer> term : terms.entrySet())
                percentageTerms.put(term.getKey(), (term.getValue() / (double) weatherList.size()));

            // fuzzification of percentage
            Map<String, String> fuzzifyTerms = new HashMap<>();
            for (Map.Entry<String, Double> term : percentageTerms.entrySet())
                fuzzifyTerms.put(term.getKey(), getFrequency(term.getValue(), fis));
            //fields.put(var, fuzzifyTerms);
        return fuzzifyTerms;
        //}

        //return fields;
    }

    private String getFrequency(double termFreq, FIS fis) {
        fis.setVariable("percentage", termFreq);
        Variable var = fis.getVariable("percentage");

        return var.getLinguisticTerms().keySet().stream()
                .map(str -> new Pair<>(str, var.getMembership(str)))
                //.filter(pair -> pair.getValue() > activation)
                .max(Comparator.comparing(Pair::getValue))
                .map(Pair::getKey)
                .orElse("");
    }
*//*
    public void generateReport() {
        List<String> variables = Arrays.asList("day_time", "cloudiness", "dampness", "wind_velocity",
                "precipitation_six", "snow", "pressure_station", "pressure_sea", "weather",
                "temperature", "temperature_wet");
        Map<String, List<String>> freq = new HashMap<>();
        Map<String, String> terms;
        for (String var : variables) {
            terms = getTerms(var);
            terms.entrySet().stream().forEach(term -> {
                List<String> newList = freq.getOrDefault(term.getValue(), new ArrayList<>());
                newList.add(term.getKey());//(var + " " + term.getKey());
                freq.put(term.getValue(), newList);
            });
        }

        StringBuilder builder = new StringBuilder();
        freq.entrySet().stream().forEach(f -> {
            builder.append(f.getKey() + " days were");
            f.getValue().stream().forEach(term -> builder.append(" " + term));
            builder.append("\n");
        });

        System.out.println(freq);
        System.out.println(builder.toString());
    }
*//*
    public void generateReport() {
        List<String> variables = Arrays.asList("day_time", "cloudiness", "dampness", "wind_velocity",
                "precipitation_six", "snow", "pressure_station", "pressure_sea", "weather",
                "temperature", "temperature_wet");
        Map<String, String> terms;
        for (String var : variables) {
            terms = getTerms(var);
            System.out.println(var + "=" + terms);
        }
        StringBuilder builder = new StringBuilder();
        terms = getTerms("temperature");
        terms.entrySet().stream().forEach(term ->
                builder.append("temperature in " + term.getValue() + " days was " + term.getKey() + "\n"));
        terms = getTerms("cloudiness");
        terms.entrySet().stream().forEach(term ->
                builder.append(term.getValue() + " days were " + term.getKey() + "\n"));
        terms = getTerms("dampness");
        terms.entrySet().stream().forEach(term ->
                builder.append("dampness was in " + term.getValue() + " days " + term.getKey() + "\n"));
        terms = getTerms("wind_velocity");
        terms.entrySet().stream().forEach(term ->
                builder.append(term.getValue() + " days were " + term.getKey() + "\n"));
        terms = getTerms("precipitation_six");
        terms.entrySet().stream().forEach(term ->
                builder.append("rain in " + term.getValue() + " days was " + term.getKey() + "\n"));
        terms = getTerms("snow");
        terms.entrySet().stream().forEach(term ->
                builder.append("in " + term.getValue() + " days there was " + term.getKey() + " snow\n"));
        terms = getTerms("pressure_station");
        terms.entrySet().stream().forEach(term ->
                builder.append("pressure at station level in " + term.getValue() + " days was " + term.getKey() + "\n"));
        terms = getTerms("pressure_sea");
        terms.entrySet().stream().forEach(term ->
                builder.append("pressure at sea level in " + term.getValue() + " days was " + term.getKey() + "\n"));
        terms = getTerms("weather");
        terms.entrySet().stream().forEach(term ->
                builder.append("overall weather in " + term.getValue() + " days was " + term.getKey() + "\n"));

        System.out.println(builder.toString());
    }
}
*/