package ru.ohayo.weather_tekom.data.remote.models



data class WeatherModel(
    val current: Current,
    val location: Location,
    val forecast: Forecast,
    val requestTime: String
)
val WeatherModelStub = WeatherModel(
current = CurrentStub,
 location = LocationStub,
 forecast = ForecastStub,
requestTime = ""

)