package moj.iot.weather.network.weather;


import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.schedulers.Schedulers;
import moj.iot.weather.model.Weather;
import moj.iot.weather.network.NetworkManager;
import retrofit2.Call;
import retrofit2.Response;

public class WeatherNetworkHelperImpl implements WeatherNetworkHelper {

    private static final int POLLING_INTERVAL = 10;

    private final NetworkManager mNetworkManager;
    private WeatherNetworkHelperListener mListener;
    private Call<Weather> mCall;
    private Disposable mWeatherDisposable;

    @Inject
    public WeatherNetworkHelperImpl(NetworkManager networkManager) {
        this.mNetworkManager = networkManager;
    }

    @Override
    public void setListener(WeatherNetworkHelperListener listener) {
        mListener = listener;
    }

    @Override
    public void requestWeather(double latitude, double longitude) {
        mWeatherDisposable = Observable.interval(0, POLLING_INTERVAL, TimeUnit.MINUTES)
                .flatMap(timer -> buildWeatherRequestObservable(latitude, longitude))
                .retry(Long.MAX_VALUE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::onSuccess)
                .doOnError(this::onError)
                .subscribe();
    }

    @Override
    public void cancel() {
        if (mCall != null) {
            mCall.cancel();
        }

        if (mWeatherDisposable != null && !mWeatherDisposable.isDisposed()) {
            mWeatherDisposable.dispose();
        }
    }

    private Observable<Weather> buildWeatherRequestObservable(double latitude, double longitude) {
        return Observable.create(subscriber -> {
            mCall = mNetworkManager.getWeather(latitude, longitude);
            Response<Weather> response = null;
            try {
                response = mCall.execute();
            } catch (SocketException e) {
                onError(e);
            } finally {
                mCall = null;
            }
            if (response != null && response.isSuccessful() && response.body() != null) {
                onSuccess(response.body());
                return;
            }
            onError(new NullPointerException("Response is not successful or null."));
        });
    }

    private void onSuccess(Weather weather) {
        if (mListener != null) {
            mListener.onRequestCompleted(weather);
        }
    }

    private void onError(Throwable throwable) {
        if (mListener != null) {
            mListener.onRequestFailed(throwable);
        }
    }
}
