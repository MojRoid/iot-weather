package moj.iot.weather.hardware.led.strip;


import android.graphics.Color;

import com.google.android.things.contrib.driver.apa102.Apa102;

import java.io.IOException;

import javax.inject.Inject;

import static com.google.android.things.contrib.driver.rainbowhat.RainbowHat.BUS_LEDSTRIP;

public class LedStripControllerImpl implements LedStripController {

    public static final int LED_COUNT = 7;
    private static final int LED_BRIGHTNESS = 1;

    private Apa102 mLedStrip;
    private int[] mColours = new int[LED_COUNT];

    @Inject
    public LedStripControllerImpl() {

        try {
            mLedStrip = new Apa102(BUS_LEDSTRIP, Apa102.Mode.BGR, Apa102.Direction.REVERSED);
            mLedStrip.setBrightness(LED_BRIGHTNESS);
            for (int i = 0; i < LED_COUNT; i++) {
                mColours[i] = Color.WHITE;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (mLedStrip != null) {
            try {
                mLedStrip.write(new int[LED_COUNT]);
                mLedStrip.setBrightness(0);
                mLedStrip.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mLedStrip = null;
            }
        }
    }

    @Override
    public void setLedColour(int position, int colour) {
        mColours[position] = colour;
        try {
            mLedStrip.write(mColours);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
