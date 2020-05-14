package Readers;

import model.Weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    public static List<Weather> readWeatherFromCsv(String filename) {
        Path path = Paths.get(System.getProperty("user.dir") + File.separator
                + "data" + File.separator + filename);
        List<Weather> weatherList = new ArrayList<>();
        System.out.println("Starting reading file: " + path.toString());
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            String line = bufferedReader.readLine();
            while (line != null && line.length() != 0) {
                String[] columns = line.replace("\"", "").split(",");
                Weather weather = convertToWeather(columns);
                weatherList.add(weather);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherList;
    }

    private static Weather convertToWeather(String[] columns) {
        LocalDateTime date = LocalDateTime.of(
                Integer.parseInt(columns[2]),
                Month.of(Integer.parseInt(columns[3])),
                Integer.parseInt(columns[4]),
                Integer.parseInt(columns[5]),
                0);
         return new Weather(
                Integer.parseInt(columns[25]),
                date,
                Double.parseDouble(columns[21]),
                Double.parseDouble(columns[29]),
                Double.parseDouble((columns[31].contains("/") || columns[31].isEmpty()) ? "0.0" : columns[31]),
                Integer.parseInt(columns[37]),
                Double.parseDouble(columns[41]),
                Double.parseDouble(columns[43]),
                Double.parseDouble(columns[48]),
                Integer.parseInt((columns[97].contains("/") || columns[97].isEmpty()) ? "0" : columns[97]));
    }
}
