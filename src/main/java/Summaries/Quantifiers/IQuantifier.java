package Summaries.Quantifiers;

import model.SimpleFuzzifyWeather;

import java.util.List;

public interface IQuantifier {
    double t1();
    List<SimpleFuzzifyWeather> getWeatherList();
    String getTerm();
}
