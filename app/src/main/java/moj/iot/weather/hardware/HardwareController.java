package moj.iot.weather.hardware;


import android.support.annotation.NonNull;

import moj.iot.weather.model.Weather;

public interface HardwareController extends BaseHardwareController {
    /**
     * Update weather on hardware.
     *
     * @param weather the {@link Weather} object which represents the current weather.
     */
    void updateWeather(@NonNull Weather weather);

    /**
     * Set the network status.
     *
     * @param isNetworkDown true if the network is down, false otherwise.
     */
    void isNetworkDown(boolean isNetworkDown);
}

