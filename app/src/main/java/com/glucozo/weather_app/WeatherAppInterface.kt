package com.glucozo.weather_app

import com.glucozo.weather_app.mode.WeatherApp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAppInterface {
    @GET("weather")
    fun getWeatherData(
        @Query("q") city: String,
        @Query("appid") appId: String,
//        @Query("units") units: String,
        ): Call<WeatherApp>
}