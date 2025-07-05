package ru.ohayo.weather_tekom.data.remote.models



data class Current(
    val condition: Condition,
    val humidity: String,
    val last_updated: String,
    val temp_c: String,
    val wind_kph: String,

)