package moj.iot.weather.hardware.buzzer;

import moj.iot.weather.hardware.BaseHardwareController;


public interface BuzzerController extends BaseHardwareController {
    /**
     * Play a frequency.
     *
     * @param frequency the frequency to play.
     */
    void playFrequency(double frequency);

    /**
     * Stop playing a frequency.
     */
    void stopPlaying();

    /**
     * Play a test audio sequence.
     */
    void playTest();

    /**
     * Play song of storms from Zelda.
     */
    void playSongOfStorms();
}
