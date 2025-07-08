package ru.ohayo.weather_tekom.repository


import ru.ohayo.weather_tekom.data.room.city.CityDao
import ru.ohayo.weather_tekom.data.room.weatherCache.WeatherDao
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val cityDao: CityDao,
    private val weatherDao: WeatherDao,
) {
}