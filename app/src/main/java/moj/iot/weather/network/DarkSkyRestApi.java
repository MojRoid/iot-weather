package moj.iot.weather.network;


import moj.iot.weather.model.Weather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface DarkSkyRestApi {

    @GET("forecast/{key}/{latitude},{longitude}")
    Call<Weather> getWeather(@Path("key") String key,
                             @Path("latitude") double latitude,
                             @Path("longitude") double longitude,
                             @Query("exclude") String excludes,
                             @Query("units") String units);
}
