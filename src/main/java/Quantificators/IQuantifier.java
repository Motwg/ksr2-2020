package Quantificators;

import model.SimpleFuzzifyWeather;

import java.util.List;

public interface IQuantifier {
    double t1(String term);
    List<SimpleFuzzifyWeather> getWeatherList();
}
