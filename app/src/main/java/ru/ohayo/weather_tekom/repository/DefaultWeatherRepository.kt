package ru.ohayo.weather_tekom.repository

import com.google.gson.Gson
import ru.ohayo.weather_tekom.data.remote.Constant
import ru.ohayo.weather_tekom.data.remote.api.NetworkResponse
import ru.ohayo.weather_tekom.data.remote.api.WeatherApi
import ru.ohayo.weather_tekom.data.remote.models.Condition
import ru.ohayo.weather_tekom.data.remote.models.Current
import ru.ohayo.weather_tekom.data.remote.models.Forecast
import ru.ohayo.weather_tekom.data.remote.models.ForecastDay
import ru.ohayo.weather_tekom.data.remote.models.Location
import ru.ohayo.weather_tekom.data.remote.models.WeatherModel
import ru.ohayo.weather_tekom.data.room.weatherCache.WeatherDao
import ru.ohayo.weather_tekom.data.room.weatherCache.WeatherDbo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class DefaultWeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    override suspend fun getWeatherData(city: String): NetworkResponse<WeatherModel> {
        return try {
            val response = weatherApi.getWeather(Constant.apiKey, city)
            if (response.isSuccessful && response.body() != null) {
                val currentTime = Date()
                val formattedTime = SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault()).format(currentTime)
                val updatedModel = response.body()!!.copy(requestTime = formattedTime)

                saveWeatherToCache(updatedModel, city)

                NetworkResponse.Success(updatedModel)
            } else {
                loadWeatherFromCache(city)?.let { cached ->
                    NetworkResponse.Success(cached)
                } ?: NetworkResponse.Error("Нет данных")
            }
        } catch (e: Exception) {
            loadWeatherFromCache(city)?.let { cached ->
                NetworkResponse.Success(cached)
            } ?: NetworkResponse.Error("Ошибка сети")
        }
    }

    override suspend fun saveWeatherToCache(model: WeatherModel, city: String) {
        val forecastJson = Gson().toJson(model.forecast.forecastday)
        val dbo = WeatherDbo(
            cityName = city,
            tempC = model.current.temp_c,
            humidity = model.current.humidity,
            windKph = model.current.wind_kph,
            conditionText = model.current.condition.text,
            conditionIcon = model.current.condition.icon,
            conditionCode = model.current.condition.code,
            lastUpdated = model.current.last_updated,
            requestTime = model.requestTime,
            forecastDays = forecastJson
        )
        weatherDao.insert(dbo)
    }

    override suspend fun loadWeatherFromCache(city: String): WeatherModel? {
        val cachedData = weatherDao.getWeather(city)
        return cachedData?.let { dbo ->
            val forecastList = Gson().fromJson(dbo.forecastDays, Array<ForecastDay>::class.java).toList()
            WeatherModel(
                location = Location(name = cachedData.cityName),
                current = Current(
                    temp_c = cachedData.tempC,
                    humidity = cachedData.humidity,
                    wind_kph = cachedData.windKph,
                    condition = Condition(
                        text = cachedData.conditionText,
                        icon = cachedData.conditionIcon,
                        code = cachedData.conditionCode
                    ),
                    last_updated = cachedData.lastUpdated,

                    ),
                forecast = Forecast(forecastday = forecastList),
                requestTime = cachedData.requestTime
            )
        }
    }
}