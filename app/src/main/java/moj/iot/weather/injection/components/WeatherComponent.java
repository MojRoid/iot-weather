package moj.iot.weather.injection.components;

import dagger.Subcomponent;
import moj.iot.weather.WeatherActivity;
import moj.iot.weather.injection.modules.HardwareModule;
import moj.iot.weather.injection.modules.WeatherModule;
import moj.iot.weather.injection.scopes.PerActivity;

@Subcomponent(modules = {WeatherModule.class, HardwareModule.class})
@PerActivity
public interface WeatherComponent {

    void inject(WeatherActivity activity);
}
