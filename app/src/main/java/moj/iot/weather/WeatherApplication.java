package moj.iot.weather;

import android.app.Application;

import moj.iot.weather.injection.components.ApplicationComponent;
import moj.iot.weather.injection.components.DaggerApplicationComponent;
import moj.iot.weather.injection.modules.ApplicationModule;
import moj.iot.weather.injection.modules.NetworkModule;
import timber.log.Timber;

public class WeatherApplication extends Application {

    private static WeatherApplication sApp;

    private ApplicationComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        sApp = this;
        initComponent();
    }

    private void initComponent() {
        mComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule())
                .build();
        mComponent.inject(this);
    }

    public static WeatherApplication getApp() {
        return sApp;
    }

    public ApplicationComponent getComponent() {
        return mComponent;
    }
}
