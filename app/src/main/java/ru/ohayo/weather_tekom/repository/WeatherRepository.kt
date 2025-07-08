package ru.ohayo.weather_tekom.repository


import ru.ohayo.weather_tekom.data.remote.api.NetworkResponse
import ru.ohayo.weather_tekom.data.remote.models.WeatherModel


interface WeatherRepository {
    suspend fun getWeatherData(city: String): NetworkResponse<WeatherModel>
    suspend fun saveWeatherToCache(model: WeatherModel, city: String)
    suspend fun loadWeatherFromCache(city: String): WeatherModel?
}