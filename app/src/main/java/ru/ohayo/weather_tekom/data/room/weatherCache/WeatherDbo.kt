package ru.ohayo.weather_tekom.data.room.weatherCache


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_cache")
data class WeatherDbo(
    @PrimaryKey val cityName: String,
    val tempC: String,
    val humidity: String,
    val windKph: String,
    val conditionText: String,
    val conditionIcon: String,
    val conditionCode: String,
    val lastUpdated: String,
    val requestTime: String,
    val forecastDays: String,
)