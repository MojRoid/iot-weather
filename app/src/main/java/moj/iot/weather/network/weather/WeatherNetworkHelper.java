package moj.iot.weather.network.weather;


public interface WeatherNetworkHelper {
    /**
     * Request the current weather.
     *
     * @param latitude  latitude.
     * @param longitude longitude.
     */
    void requestWeather(double latitude, double longitude);

    /**
     * Set the listener to callback to.
     *
     * @param listener The listener that will be called back to.
     */
    void setListener(WeatherNetworkHelperListener listener);

    /**
     * Cancel any running network requests.
     */
    void cancel();
}
