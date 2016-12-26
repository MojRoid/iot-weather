package moj.iot.weather.hardware.led.single;

import moj.iot.weather.hardware.BaseHardwareController;

public interface LedController extends BaseHardwareController {

    /**
     * Set the state of the LED.
     *
     * @param enabled true to enable the LED, false otherwise.
     */
    void isEnabled(boolean enabled);
}
