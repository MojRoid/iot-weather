package moj.iot.weather.injection.modules;


import android.content.Context;

import dagger.Module;
import dagger.Provides;
import moj.iot.weather.BuildConfig;
import moj.iot.weather.WeatherApplication;
import moj.iot.weather.injection.qualifiers.ForApplication;
import moj.iot.weather.injection.scopes.PerApplication;
import moj.iot.weather.model.DeviceLocation;

@Module
public class ApplicationModule {

    private static final double LATITUDE = BuildConfig.DEFAULT_DEVICE_LATITUDE;
    private static final double LONGITUDE = BuildConfig.DEFAULT_DEVICE_LONGITUDE;

    private final WeatherApplication mApplication;

    public ApplicationModule(WeatherApplication application) {
        mApplication = application;
    }

    @Provides
    @PerApplication
    @ForApplication
    Context providesApplicationContext() {
        return mApplication;
    }

    @Provides
    @PerApplication
    DeviceLocation providesDeviceLocation() {
        return new DeviceLocation(LATITUDE, LONGITUDE);
    }
}
