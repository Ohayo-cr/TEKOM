package ru.ohayo.weather_tekom.data.remote.models

import org.threeten.bp.Instant


data class WeatherModel(
    val current: Current,
    val location: Location,
    val forecast: Forecast,
    val requestTime: Instant
)