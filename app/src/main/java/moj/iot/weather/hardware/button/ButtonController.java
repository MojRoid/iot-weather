package moj.iot.weather.hardware.button;

import com.google.android.things.contrib.driver.button.Button;

import moj.iot.weather.hardware.BaseHardwareController;

public interface ButtonController extends BaseHardwareController {
    /**
     * Set the listener for the button to call back to.
     *
     * @param listener The event listener.
     */
    void setListener(Button.OnButtonEventListener listener);

    /**
     * Compares a {@link Button} object and determines if they are the same.
     *
     * @param button object to compare.
     * @return true if buttons are the same, false otherwise.
     */
    boolean equals(Button button);
}
