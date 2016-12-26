package moj.iot.weather.injection.components;

import dagger.Component;
import moj.iot.weather.WeatherApplication;
import moj.iot.weather.injection.modules.ApplicationModule;
import moj.iot.weather.injection.modules.NetworkModule;
import moj.iot.weather.injection.modules.WeatherModule;
import moj.iot.weather.injection.scopes.PerApplication;

@Component(modules = {ApplicationModule.class, NetworkModule.class})
@PerApplication
public interface ApplicationComponent {

    void inject(WeatherApplication application);

    WeatherComponent plus(WeatherModule module);
}
