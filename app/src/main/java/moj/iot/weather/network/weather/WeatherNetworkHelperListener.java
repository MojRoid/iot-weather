package moj.iot.weather.network.weather;


import moj.iot.weather.model.Weather;

public interface WeatherNetworkHelperListener {
    void onRequestCompleted(Weather weather);

    void onRequestFailed(Throwable throwable);
}
