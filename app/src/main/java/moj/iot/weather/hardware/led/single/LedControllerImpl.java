package moj.iot.weather.hardware.led.single;


import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.pio.Gpio;

import java.io.IOException;

public class LedControllerImpl implements LedController {

    private Gpio mLed;

    public LedControllerImpl(@RainbowHat.LedPin String colour) {
        try {
            mLed = RainbowHat.openLed(colour);
            mLed.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (mLed != null) {
            try {
                mLed.setValue(false);
                mLed.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mLed = null;
            }
        }
    }

    @Override
    public void isEnabled(boolean enabled) {
        try {
            mLed.setValue(enabled);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
