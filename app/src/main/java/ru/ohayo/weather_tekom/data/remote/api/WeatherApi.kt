package ru.ohayo.weather_tekom.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.ohayo.weather_tekom.data.remote.models.WeatherModel

interface WeatherApi {


    @GET("/v1/forecast.json")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("days") days: Int = 3,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no",
        @Query("lang") lang: String = "ru"
    ) : Response<WeatherModel>

}
//http://api.weatherapi.com/v1/forecast.json?key=687232b51fa84f55b1c171028250402&q=London&days=3&aqi=no&alerts=no
//http://api.weatherapi.com/v1/current.json?key=687232b51fa84f55b1c171028250402&q=London&aqi=no
