package moj.iot.weather.hardware;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.things.contrib.driver.button.Button;

import org.joda.time.DateTime;

import javax.inject.Inject;

import moj.iot.weather.R;
import moj.iot.weather.WeatherController;
import moj.iot.weather.hardware.button.ButtonController;
import moj.iot.weather.hardware.buzzer.BuzzerController;
import moj.iot.weather.hardware.display.DisplayController;
import moj.iot.weather.hardware.led.single.LedController;
import moj.iot.weather.hardware.led.strip.LedStripController;
import moj.iot.weather.injection.qualifiers.ForActivity;
import moj.iot.weather.injection.qualifiers.ForButtonA;
import moj.iot.weather.injection.qualifiers.ForButtonB;
import moj.iot.weather.injection.qualifiers.ForButtonC;
import moj.iot.weather.injection.qualifiers.ForLedBlue;
import moj.iot.weather.injection.qualifiers.ForLedGreen;
import moj.iot.weather.injection.qualifiers.ForLedRed;
import moj.iot.weather.model.Minute;
import moj.iot.weather.model.Weather;
import moj.iot.weather.util.WeatherUtils;

import static moj.iot.weather.hardware.led.strip.LedStripControllerImpl.LED_COUNT;
import static moj.iot.weather.util.WeatherUtils.PRECIPITATION_PROBABILITY_THRESHOLD;

public class HardwareControllerImpl implements HardwareController, Button.OnButtonEventListener {

    private final Context mContext;
    private final WeatherController mWeatherController;
    private final ButtonController mButtonA;
    private final ButtonController mButtonB;
    private final ButtonController mButtonC;
    private final BuzzerController mBuzzer;
    private final DisplayController mDisplay;
    private final LedController mLedRed;
    private final LedController mLedGreen;
    private final LedController mLedBlue;
    private final LedStripController mLedStrip;


    private Weather mWeather;
    private boolean mIsRainingForNextHour;
    private boolean mIsNetworkDown;

    @Inject
    public HardwareControllerImpl(@ForActivity Context context, WeatherController weatherController,
                                  @ForButtonA ButtonController buttonA, @ForButtonB ButtonController buttonB,
                                  @ForButtonC ButtonController buttonC, BuzzerController buzzer,
                                  DisplayController display, @ForLedRed LedController ledRed,
                                  @ForLedGreen LedController ledGreen, @ForLedBlue LedController ledBlue,
                                  LedStripController ledStrip) {
        mContext = context;
        mWeatherController = weatherController;
        mButtonA = buttonA;
        mButtonB = buttonB;
        mButtonC = buttonC;
        mBuzzer = buzzer;
        mDisplay = display;
        mLedRed = ledRed;
        mLedGreen = ledGreen;
        mLedBlue = ledBlue;
        mLedStrip = ledStrip;

        initButtonListeners();
    }

    private void initButtonListeners() {
        mButtonA.setListener(this);
        mButtonB.setListener(this);
        mButtonC.setListener(this);
    }

    @Override
    public void close() {
        mButtonA.close();
        mButtonB.close();
        mButtonC.close();
        mBuzzer.close();
        mDisplay.close();
        mLedRed.close();
        mLedGreen.close();
        mLedBlue.close();
        mLedStrip.close();
    }

    @Override
    public void updateWeather(@NonNull Weather weather) {
        mWeather = weather;
        displayTemperature(weather);
        setLedStripColours(weather);
        setRainStatus(weather);
    }

    @Override
    public void isNetworkDown(boolean isNetworkDown) {
        mIsNetworkDown = isNetworkDown;
        mLedRed.isEnabled(isNetworkDown);
        mLedGreen.isEnabled(!isNetworkDown);
    }

    @Override
    public void onButtonEvent(Button button, boolean pressed) {
        if (mButtonA.equals(button)) {
            if (pressed && mIsNetworkDown) {
                mIsNetworkDown = false;
                mLedRed.isEnabled(false);
                mWeatherController.requestWeather();
            }
        }

        if (mButtonB.equals(button)) {
            if (pressed) {
                mBuzzer.playTest();
            }
        }

        if (mButtonC.equals(button)) {
            if (pressed) {
                displayNextRain(mWeather);
            } else {
                displayTemperature(mWeather);
            }
        }
    }

    private void setLedStripColours(@NonNull Weather weather) {
        int pastTemperature = 0;
        for (int h = 0; h < LED_COUNT; h++) {
            int currentTemperature = (int) (weather.getHourly().getHours().get(h).getTemperature() + 0.5);
            double precipProbability = weather.getHourly().getHours().get(h).getPrecipProbability();
            int colour;
            if (precipProbability > PRECIPITATION_PROBABILITY_THRESHOLD) {
                colour = mContext.getColor(R.color.rain);
            } else if (h == 0) {
                colour = mContext.getColor(R.color.white);
            } else if (currentTemperature > pastTemperature) {
                colour = mContext.getColor(R.color.warmer);
            } else if (currentTemperature < pastTemperature) {
                colour = mContext.getColor(R.color.colder);
            } else {
                colour = mContext.getColor(R.color.white);
            }
            pastTemperature = (int) (weather.getHourly().getHours().get(h).getTemperature() + 0.5);
            mLedStrip.setLedColour(h, colour);
        }
    }

    private void setRainStatus(@NonNull Weather weather) {
        boolean isRaining = WeatherUtils.isRainLikelyWithinNextHour(weather.getMinutely().getMinutes());
        mLedBlue.isEnabled(isRaining);

        if (isRaining && !mIsRainingForNextHour) {
            if (!WeatherUtils.isSleepingHours()) {
                mBuzzer.playSongOfStorms();
            }
        }
        mIsRainingForNextHour = isRaining;
    }

    private void displayNextRain(@Nullable Weather weather) {
        if (weather == null) {
            return;
        }
        Minute nextRainMinute = WeatherUtils.getNextRainMinute(weather.getMinutely().getMinutes());

        if (nextRainMinute != null) {
            DateTime currentDateTime = new DateTime(System.currentTimeMillis());
            int hour = nextRainMinute.getTime().getHourOfDay() == currentDateTime.getHourOfDay() ? 0 : 60;
            int timeDifference = (nextRainMinute.getTime().getMinuteOfHour() + hour) - currentDateTime.getMinuteOfHour();
            if (timeDifference <= 0) {
                mDisplay.display(mContext.getString(R.string.now));
            } else {
                mDisplay.display(mContext.getString(R.string.in) + timeDifference);
            }
        }
    }

    private void displayTemperature(@Nullable Weather weather) {
        if (weather == null) {
            return;
        }
        String formattedTemperature = WeatherUtils
                .getFormattedTemperature(weather.getHourly().getHours().get(0).getTemperature());
        mDisplay.display(formattedTemperature);
    }
}
