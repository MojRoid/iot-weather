package moj.iot.weather.hardware.display;


import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.ht16k33.Ht16k33;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

import java.io.IOException;

import javax.inject.Inject;

public class DisplayControllerImpl implements DisplayController {

    private AlphanumericDisplay mDisplay;

    @Inject
    public DisplayControllerImpl() {
        try {
            mDisplay = RainbowHat.openDisplay();
            mDisplay.setEnabled(true);
            mDisplay.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX);
            mDisplay.display("----");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (mDisplay != null) {
            try {
                mDisplay.clear();
                mDisplay.setEnabled(false);
                mDisplay.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mDisplay = null;
            }
        }
    }

    @Override
    public void display(String text) {
        try {
            mDisplay.display(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void display(int number) {
        try {
            mDisplay.display(number);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void display(double number) {
        try {
            mDisplay.display(number);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
