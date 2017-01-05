package moj.iot.weather.util;


import android.support.annotation.NonNull;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import moj.iot.weather.model.Minute;

public class WeatherUtils {

    public static final double PRECIPITATION_PROBABILITY_THRESHOLD = 0.5;
    private static final int SLEEP_START = 23;
    private static final int SLEEP_END = 6;

    /**
     * Returns a list of old minutes.
     *
     * @param minutes The list of minutes.
     * @return List of {@link Minute} objects that occur before the current time.
     */
    public static List<Minute> getOldMinutes(@NonNull List<Minute> minutes) {
        List<Minute> oldMinutes = new ArrayList<>();
        for (Minute minute : minutes) {
            if (minute.getTime().plusMinutes(1).isBeforeNow()) {
                oldMinutes.add(minute);
            } else {
                break;
            }
        }
        return oldMinutes;
    }

    /**
     * Checks if rain is likely to occur within the next hour (assuming the list of minutes is for an hour).
     *
     * @param minutes The list of minutes that need checking. This should be a hour worth of minutes.
     * @return true if rain is likely to occur within the next hour, false otherwise.
     */
    public static boolean isRainLikelyWithinNextHour(@NonNull List<Minute> minutes) {
        for (Minute minute : minutes) {
            if (minute.getPrecipProbability() > PRECIPITATION_PROBABILITY_THRESHOLD) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the minute where rain is more than PRECIPITATION_PROBABILITY_THRESHOLD.
     *
     * @param minutes The list of minutes to check.
     * @return The minute that is likely to have rain.
     */
    public static Minute getNextRainMinute(@NonNull List<Minute> minutes) {
        Minute nextRainMinute = null;
        for (Minute minute : minutes) {
            if (minute.getPrecipProbability() > PRECIPITATION_PROBABILITY_THRESHOLD) {
                nextRainMinute = minute;
                break;
            }
        }
        return nextRainMinute;
    }

    /**
     * Formats the temperature as a string to set centre of the screen.
     *
     * @param temperature the temperature to format.
     * @return formatted string containing the temperate with its unit.
     */
    public static String getFormattedTemperature(double temperature) {
        String formattedTemperature;
        String defaultUnit = "c";
        int roundedTemperature = (int) (temperature + 0.5);
        if (roundedTemperature < 0 || roundedTemperature >= 10) {
            formattedTemperature = String.format(Locale.ENGLISH, "%d%s", roundedTemperature, defaultUnit);
        } else {
            formattedTemperature = String.format(Locale.ENGLISH, " %d%s", roundedTemperature, defaultUnit);
        }
        return formattedTemperature;
    }

    public static boolean isSleepingHours() {
        DateTime currentDateTime = new DateTime(System.currentTimeMillis());
        if (currentDateTime.getHourOfDay() >= SLEEP_START || currentDateTime.getHourOfDay() <= SLEEP_END) {
            return true;
        }
        return false;
    }
}
