package moj.iot.weather;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import moj.iot.weather.hardware.HardwareController;
import moj.iot.weather.injection.modules.WeatherModule;
import moj.iot.weather.model.DeviceLocation;
import moj.iot.weather.model.Weather;
import moj.iot.weather.network.weather.WeatherNetworkHelper;
import moj.iot.weather.network.weather.WeatherNetworkHelperListener;
import moj.iot.weather.util.WeatherUtils;

public class WeatherActivity extends Activity implements WeatherController, WeatherNetworkHelperListener {

    private static final long INTERVAL_PERIOD = TimeUnit.MINUTES.toMillis(1);

    @Inject
    DeviceLocation mLocation;

    @Inject
    WeatherNetworkHelper mNetwork;

    @Inject
    HardwareController mHardware;

    private Weather mWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
        initNetwork();
        initScheduling();
    }

    @Override
    protected void onDestroy() {
        mNetwork.cancel();
        mHardware.close();
        super.onDestroy();
    }

    private void initComponent() {
        WeatherApplication
                .getApp()
                .getComponent()
                .plus(new WeatherModule(this))
                .inject(this);
    }

    private void initNetwork() {
        mNetwork.setListener(this);
        mNetwork.requestWeather(mLocation.getLatitude(), mLocation.getLongitude());
    }

    private void initScheduling() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (mWeather == null) {
                    return;
                }
                mWeather.getMinutely()
                        .getMinutes()
                        .removeAll(WeatherUtils.getOldMinutes(mWeather.getMinutely().getMinutes()));
                mHardware.updateWeather(mWeather);
                handler.postDelayed(this, INTERVAL_PERIOD);
            }
        }, INTERVAL_PERIOD);
    }

    @Override
    public void onRequestCompleted(Weather weather) {
        if (mWeather == null) {
            mHardware.updateWeather(weather);
        }
        mWeather = weather;
        mHardware.isNetworkDown(false);
    }

    @Override
    public void onRequestFailed(Throwable throwable) {
        throwable.printStackTrace();
        mHardware.isNetworkDown(true);
    }

    @Override
    public void requestWeather() {
        mNetwork.requestWeather(mLocation.getLatitude(), mLocation.getLongitude());
    }
}
