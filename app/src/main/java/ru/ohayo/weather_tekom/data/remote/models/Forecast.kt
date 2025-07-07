package ru.ohayo.weather_tekom.data.remote.models



data class Forecast(
    val forecastday: List<ForecastDay>
)
val ForecastStub = Forecast(
    forecastday = listOf(ForecastDayStub)
)