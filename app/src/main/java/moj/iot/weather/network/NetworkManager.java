package moj.iot.weather.network;

import moj.iot.weather.model.Weather;
import retrofit2.Call;

public interface NetworkManager {
    /**
     * Get the current weather.
     *
     * @param latitude  latitude.
     * @param longitude longitude.
     * @return a {@link Weather} object which contains weather information.
     */
    Call<Weather> getWeather(double latitude, double longitude);
}
