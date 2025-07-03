package ru.ohayo.weather_tekom.domain.api

data class WeatherModel(
    val current: Current,
    val location: Location
)