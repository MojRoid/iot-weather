package moj.iot.weather.hardware.display;

import moj.iot.weather.hardware.BaseHardwareController;

public interface DisplayController extends BaseHardwareController {
    /**
     * Display a text. Maximum 4 characters at a time.
     *
     * @param text Text to display as a {@link String}.
     */
    void display(String text);

    /**
     * Display a text. Maximum 4 characters at a time.
     *
     * @param number Text to display as an int.
     */
    void display(int number);

    /**
     * Display a text. Maximum 4 characters at a time.
     *
     * @param number Text to display as an double.
     */
    void display(double number);
}
