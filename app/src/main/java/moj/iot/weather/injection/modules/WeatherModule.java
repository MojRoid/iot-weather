package moj.iot.weather.injection.modules;


import android.content.Context;

import dagger.Module;
import dagger.Provides;
import moj.iot.weather.WeatherActivity;
import moj.iot.weather.WeatherController;
import moj.iot.weather.injection.qualifiers.ForActivity;
import moj.iot.weather.injection.scopes.PerActivity;

@Module
public class WeatherModule {

    private final WeatherActivity mActivity;

    public WeatherModule(WeatherActivity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    WeatherController providesWeatherController() {
        return mActivity;
    }

    @Provides
    @PerActivity
    @ForActivity
    Context providesActivityContext() {
        return mActivity;
    }
}
