package moj.iot.weather.injection.modules;


import android.content.Context;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import moj.iot.weather.BuildConfig;
import moj.iot.weather.R;
import moj.iot.weather.injection.qualifiers.ForApiKey;
import moj.iot.weather.injection.qualifiers.ForApplication;
import moj.iot.weather.injection.scopes.PerApplication;
import moj.iot.weather.network.NetworkManager;
import moj.iot.weather.network.NetworkManagerImpl;
import moj.iot.weather.network.weather.WeatherNetworkHelper;
import moj.iot.weather.network.weather.WeatherNetworkHelperImpl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @PerApplication
    NetworkManager providesNetworkManager(NetworkManagerImpl networkManager) {
        return networkManager;
    }

    @Provides
    @PerApplication
    OkHttpClient.Builder providesOkHttpClient() {
        return new OkHttpClient().newBuilder();
    }

    @Provides
    @PerApplication
    HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor();
    }

    @Provides
    @PerApplication
    Gson providesGson() {
        return new Gson();
    }

    @Provides
    @PerApplication
    Retrofit providesRetrofitBuilder(Gson gson, OkHttpClient.Builder httpClientBuilder,
                                     HttpLoggingInterceptor loggingInterceptor) {
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.interceptors().add(loggingInterceptor);
        OkHttpClient httpClient = httpClientBuilder.build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.DARK_SKY_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();
    }

    @Provides
    @PerApplication
    @ForApiKey
    String providesDarkSkyApiKey(@ForApplication Context context) {
        return context.getString(R.string.dark_sky_key);
    }

    @Provides
    @PerApplication
    WeatherNetworkHelper providesWeatherNetworkHelper(WeatherNetworkHelperImpl weatherNetworkHelper) {
        return weatherNetworkHelper;
    }
}
