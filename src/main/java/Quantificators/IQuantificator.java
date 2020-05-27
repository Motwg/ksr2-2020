package Quantificators;

import model.SimpleFuzzifyWeather;

import java.util.List;

public interface IQuantificator {
    double t1();
    List<SimpleFuzzifyWeather> getWeatherList();
    String getTerm();
}
