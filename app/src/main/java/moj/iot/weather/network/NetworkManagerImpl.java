package moj.iot.weather.network;


import javax.inject.Inject;

import moj.iot.weather.injection.qualifiers.ForApiKey;
import moj.iot.weather.model.Weather;
import retrofit2.Call;
import retrofit2.Retrofit;

public class NetworkManagerImpl implements NetworkManager {

    private final DarkSkyRestApi mDarkSkyRestApi;
    private final String mDarkSkyApiKey;

    @Inject
    public NetworkManagerImpl(Retrofit marvelApi, @ForApiKey String darkSkyApiKey) {
        mDarkSkyRestApi = marvelApi.create(DarkSkyRestApi.class);
        mDarkSkyApiKey = darkSkyApiKey;
    }

    @Override
    public Call<Weather> getWeather(double latitude, double longitude) {
        String excludes = "currently,daily,alerts,flags";
        String units = "uk2";
        return mDarkSkyRestApi.getWeather(mDarkSkyApiKey, latitude, longitude, excludes, units);
    }
}
