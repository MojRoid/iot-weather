package moj.iot.weather.hardware.buzzer;


import android.os.Handler;

import com.google.android.things.contrib.driver.pwmspeaker.Speaker;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;

import java.io.IOException;

import javax.inject.Inject;

import static android.os.Looper.getMainLooper;
import static moj.iot.weather.hardware.buzzer.Delays.DELAY_150;
import static moj.iot.weather.hardware.buzzer.Delays.DELAY_200;
import static moj.iot.weather.hardware.buzzer.Delays.DELAY_250;
import static moj.iot.weather.hardware.buzzer.Delays.DELAY_300;
import static moj.iot.weather.hardware.buzzer.Delays.DELAY_400;
import static moj.iot.weather.hardware.buzzer.Delays.DELAY_600;
import static moj.iot.weather.hardware.buzzer.Notes.NOTE_A_5;
import static moj.iot.weather.hardware.buzzer.Notes.NOTE_C_6;
import static moj.iot.weather.hardware.buzzer.Notes.NOTE_D_5;
import static moj.iot.weather.hardware.buzzer.Notes.NOTE_D_6;
import static moj.iot.weather.hardware.buzzer.Notes.NOTE_E_5;
import static moj.iot.weather.hardware.buzzer.Notes.NOTE_E_6;
import static moj.iot.weather.hardware.buzzer.Notes.NOTE_F_5;
import static moj.iot.weather.hardware.buzzer.Notes.NOTE_F_6;
import static moj.iot.weather.hardware.buzzer.Notes.NOTE_G_5;

public class BuzzerControllerImpl implements BuzzerController {


    private Speaker mBuzzer;
    private boolean mCurrentlyPlaying;

    @Inject
    public BuzzerControllerImpl() {
        try {
            mBuzzer = RainbowHat.openPiezo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (mBuzzer != null) {
            try {
                mBuzzer.stop();
                mBuzzer.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mBuzzer = null;
            }
        }
    }

    @Override
    public void playFrequency(double frequency) {
        try {
            mBuzzer.play(frequency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopPlaying() {
        try {
            mBuzzer.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playTest() {
        if (mCurrentlyPlaying) {
            return;
        }
        mCurrentlyPlaying = true;

        playNoteAfterDelay(100, 0);
        playNoteAfterDelay(100, 0);
        playNoteAfterDelay(1000, 100);
        playNoteAfterDelay(2000, 200);
        playNoteAfterDelay(3000, 300);
        stopPlayingAfterDelay(true, 400);
    }

    @Override
    public void playSongOfStorms() {
        if (mCurrentlyPlaying) {
            return;
        }
        mCurrentlyPlaying = true;

        int delay = 0;
        delay = songOfStormsMelody(delay);
        delay = songOfStormsMelodyEndFirst(delay);

        //---

        delay = songOfStormsMelody(delay);
        songOfStormsMelodyEndSecond(delay);

    }

    private int songOfStormsMelody(int delay) {
        playNoteAfterDelay(NOTE_D_5, delay);
        delay += DELAY_150;
        playNoteAfterDelay(NOTE_F_5, delay);
        delay += DELAY_150;
        playNoteAfterDelay(NOTE_D_6, delay);
        delay += DELAY_200;
        stopPlayingAfterDelay(false, delay);
        delay += DELAY_400;

        playNoteAfterDelay(NOTE_D_5, delay);
        delay += DELAY_150;
        playNoteAfterDelay(NOTE_F_5, delay);
        delay += DELAY_150;
        playNoteAfterDelay(NOTE_D_6, delay);
        delay += DELAY_200;
        stopPlayingAfterDelay(false, delay);
        delay += DELAY_400;

        playNoteAfterDelay(NOTE_E_6, delay);
        delay += DELAY_400;
        playNoteAfterDelay(NOTE_F_6, delay);
        delay += DELAY_150;
        playNoteAfterDelay(NOTE_E_6, delay);
        delay += DELAY_150;
        playNoteAfterDelay(NOTE_F_6, delay);
        delay += DELAY_150;
        playNoteAfterDelay(NOTE_E_6, delay);
        delay += DELAY_150;
        playNoteAfterDelay(NOTE_C_6, delay);
        delay += DELAY_150;
        playNoteAfterDelay(NOTE_A_5, delay);
        delay += DELAY_300;
        stopPlayingAfterDelay(false, delay);
        delay += DELAY_300;

        playNoteAfterDelay(NOTE_A_5, delay);
        delay += DELAY_300;
        playNoteAfterDelay(NOTE_D_5, delay);
        delay += DELAY_250;
        playNoteAfterDelay(NOTE_F_5, delay);
        delay += DELAY_150;
        playNoteAfterDelay(NOTE_G_5, delay);
        delay += DELAY_150;
        playNoteAfterDelay(NOTE_A_5, delay);
        delay += DELAY_300;
        return delay;
    }

    private int songOfStormsMelodyEndFirst(int delay) {
        stopPlayingAfterDelay(false, delay);
        delay += DELAY_600;

        playNoteAfterDelay(NOTE_A_5, delay);
        delay += DELAY_300;
        playNoteAfterDelay(NOTE_D_5, delay);
        delay += DELAY_250;
        playNoteAfterDelay(NOTE_F_5, delay);
        delay += DELAY_150;
        playNoteAfterDelay(NOTE_G_5, delay);
        delay += DELAY_150;
        playNoteAfterDelay(NOTE_E_5, delay);
        delay += DELAY_300;
        stopPlayingAfterDelay(false, delay);
        delay += DELAY_600;
        return delay;
    }

    private void songOfStormsMelodyEndSecond(int delay) {
        stopPlayingAfterDelay(false, delay);
        delay += DELAY_300;

        playNoteAfterDelay(NOTE_A_5, delay);
        delay += DELAY_300;
        playNoteAfterDelay(NOTE_D_5, delay);
        delay += DELAY_600;
        stopPlayingAfterDelay(true, delay);
    }

    private void playNoteAfterDelay(double note, int delayMillis) {
        new Handler(getMainLooper()).postDelayed(() -> playFrequency(note), delayMillis);
    }

    private void stopPlayingAfterDelay(boolean finishedPlaying, int delayMillis) {
        new Handler(getMainLooper()).postDelayed(() -> {
            if (finishedPlaying) {
                mCurrentlyPlaying = false;
            }
            stopPlaying();
        }, delayMillis);
    }
}
