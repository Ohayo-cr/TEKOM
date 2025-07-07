package ru.ohayo.weather_tekom.data.remote.models

data class ForecastDay(
    val date: String,
    val day: Day
)

val ForecastDayStub = ForecastDay(
    date = "",
    day = DayStub
)