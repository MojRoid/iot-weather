package moj.iot.weather.hardware.button;


import com.google.android.things.contrib.driver.button.Button;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

import java.io.IOException;

public class ButtonControllerImpl implements ButtonController {

    private Button mButton;

    public ButtonControllerImpl(@RainbowHat.ButtonPin String button) {
        try {
            mButton = RainbowHat.openButton(button);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (mButton != null) {
            try {
                mButton.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mButton = null;
            }
        }
    }

    @Override
    public void setListener(Button.OnButtonEventListener listener) {
        mButton.setOnButtonEventListener(listener);
    }

    @Override
    public boolean equals(Button button) {
        return mButton == button;
    }
}
