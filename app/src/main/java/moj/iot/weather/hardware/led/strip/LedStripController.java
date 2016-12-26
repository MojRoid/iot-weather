package moj.iot.weather.hardware.led.strip;

import moj.iot.weather.hardware.BaseHardwareController;

public interface LedStripController extends BaseHardwareController {

    /**
     * Set the colour of a single LED on the LED strip. Maximum 7 LED's.
     *
     * @param position the led position to set.
     * @param colour   the colour (represented as an int) to set the LED.
     */
    void setLedColour(int position, int colour);
}
