package model;

import enumerate.Season;

import java.time.LocalDateTime;

public class SeasonFactory {

    private static final int SPRING_STARTING_DAY = 21;
    private static final int SPRING_STARTING_MONTH = 3;

    private static final int SUMMER_STARTING_DAY = 22;
    private static final int SUMMER_STARTING_MONTH = 6;

    private static final int AUTUMN_STARTING_DAY = 23;
    private static final int AUTUMN_STARTING_MONTH = 9;

    private static final int WINTER_STARTING_DAY = 22;
    private static final int WINTER_STARTING_MONTH = 12;

    public static Season getSeason(LocalDateTime date) {
        if (date.getMonthValue() < SPRING_STARTING_MONTH) {
            return Season.Winter;
        } else if (date.getMonthValue() == SPRING_STARTING_MONTH) {
            if (date.getDayOfMonth() < SPRING_STARTING_DAY) {
                return Season.Winter;
            } else {
                return Season.Spring;
            }
        } else {
            if (date.getMonthValue() < SUMMER_STARTING_MONTH) {
                return Season.Spring;
            } else if (date.getMonthValue() == SUMMER_STARTING_MONTH) {
                if (date.getDayOfMonth() < SUMMER_STARTING_DAY) {
                    return Season.Spring;
                } else {
                    return Season.Summer;
                }
            } else {
                if (date.getMonthValue() < AUTUMN_STARTING_MONTH) {
                    return Season.Summer;
                } else if (date.getMonthValue() == AUTUMN_STARTING_MONTH) {
                    if (date.getDayOfMonth() < AUTUMN_STARTING_DAY) {
                        return Season.Summer;
                    } else {
                        return Season.Autumn;
                    }
                } else {
                    if (date.getMonthValue() < WINTER_STARTING_MONTH) {
                        return Season.Autumn;
                    } else {
                        if (date.getDayOfMonth() < WINTER_STARTING_DAY) {
                            return Season.Autumn;
                        } else {
                            return Season.Winter;
                        }
                    }
                }
            }
        }
    }
}
