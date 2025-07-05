package ru.ohayo.weather_tekom.data.remote.models



data class WeatherModel(
    val current: Current,
    val location: Location,
    val forecast: Forecast
)