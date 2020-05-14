package utils;

import javafx.util.Pair;

import java.util.List;

public class AndOr {
    public static Pair<String, Double> and(Pair<String, Double> first, Pair<String, Double> second) {
        return new Pair<>(
                first.getKey() + " and " + second.getKey(),
                first.getValue() < second.getValue() ? first.getValue() : second.getValue());
    }

    public static Pair<String, Double> and(List<Pair<String, Double>> terms) {
        Pair<String, Double> term = terms.get(0);
        for (int i = 1; i < terms.size(); ++i) {
            term = and(term, terms.get(i));
        }
        return term;
    }

    public static Pair<String, Double> or(Pair<String, Double> first, Pair<String, Double> second) {
        return new Pair<>(
                first.getKey() + " or " + second.getKey(),
                first.getValue() > second.getValue() ? first.getValue() : second.getValue());
    }

    public static Pair<String, Double> or(List<Pair<String, Double>> terms) {
        Pair<String, Double> term = terms.get(0);
        for (int i = 1; i < terms.size(); ++i) {
            term = or(term, terms.get(i));
        }
        return term;
    }
}
