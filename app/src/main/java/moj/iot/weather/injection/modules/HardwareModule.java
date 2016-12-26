package moj.iot.weather.injection.modules;

import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

import dagger.Module;
import dagger.Provides;
import moj.iot.weather.hardware.HardwareController;
import moj.iot.weather.hardware.HardwareControllerImpl;
import moj.iot.weather.hardware.button.ButtonController;
import moj.iot.weather.hardware.button.ButtonControllerImpl;
import moj.iot.weather.hardware.buzzer.BuzzerController;
import moj.iot.weather.hardware.buzzer.BuzzerControllerImpl;
import moj.iot.weather.hardware.display.DisplayController;
import moj.iot.weather.hardware.display.DisplayControllerImpl;
import moj.iot.weather.hardware.led.single.LedController;
import moj.iot.weather.hardware.led.single.LedControllerImpl;
import moj.iot.weather.hardware.led.strip.LedStripController;
import moj.iot.weather.hardware.led.strip.LedStripControllerImpl;
import moj.iot.weather.injection.qualifiers.ForButtonA;
import moj.iot.weather.injection.qualifiers.ForButtonB;
import moj.iot.weather.injection.qualifiers.ForButtonC;
import moj.iot.weather.injection.qualifiers.ForLedBlue;
import moj.iot.weather.injection.qualifiers.ForLedGreen;
import moj.iot.weather.injection.qualifiers.ForLedRed;
import moj.iot.weather.injection.scopes.PerActivity;

@Module
public class HardwareModule {

    @Provides
    @PerActivity
    HardwareController providesHardwareController(HardwareControllerImpl hardwareController) {
        return hardwareController;
    }

    @Provides
    @PerActivity
    @ForButtonA
    ButtonController providesButtonAController() {
        return new ButtonControllerImpl(RainbowHat.BUTTON_A);
    }

    @Provides
    @PerActivity
    @ForButtonB
    ButtonController providesButtonBController() {
        return new ButtonControllerImpl(RainbowHat.BUTTON_B);
    }

    @Provides
    @PerActivity
    @ForButtonC
    ButtonController providesButtonCController() {
        return new ButtonControllerImpl(RainbowHat.BUTTON_C);
    }

    @Provides
    @PerActivity
    BuzzerController providesBuzzerController(BuzzerControllerImpl buzzerController) {
        return buzzerController;
    }

    @Provides
    @PerActivity
    DisplayController providesDisplayController(DisplayControllerImpl displayController) {
        return displayController;
    }

    @Provides
    @PerActivity
    @ForLedRed
    LedController providesLedRedController() {
        return new LedControllerImpl(RainbowHat.LED_RED);
    }

    @Provides
    @PerActivity
    @ForLedGreen
    LedController providesLedGreenController() {
        return new LedControllerImpl(RainbowHat.LED_GREEN);
    }

    @Provides
    @PerActivity
    @ForLedBlue
    LedController providesLedBlueController() {
        return new LedControllerImpl(RainbowHat.LED_BLUE);
    }

    @Provides
    @PerActivity
    LedStripController providesLedStripController(LedStripControllerImpl ledStripController) {
        return ledStripController;
    }
}
