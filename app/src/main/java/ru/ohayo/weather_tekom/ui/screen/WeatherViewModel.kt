package ru.ohayo.weather_tekom.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.ohayo.weather_tekom.data.remote.Constant
import ru.ohayo.weather_tekom.data.remote.api.NetworkResponse
import ru.ohayo.weather_tekom.data.remote.api.RetrofitInstance
import ru.ohayo.weather_tekom.data.remote.models.Condition
import ru.ohayo.weather_tekom.data.remote.models.Current
import ru.ohayo.weather_tekom.data.remote.models.Forecast
import ru.ohayo.weather_tekom.data.remote.models.ForecastDay
import ru.ohayo.weather_tekom.data.remote.models.Location
import ru.ohayo.weather_tekom.data.remote.models.WeatherModel
import ru.ohayo.weather_tekom.data.room.weatherCache.WeatherDao
import ru.ohayo.weather_tekom.data.room.weatherCache.WeatherDbo
import ru.ohayo.weather_tekom.repository.CityRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel@Inject constructor(
    private val cityRepository: CityRepository,
    private val weatherDao: WeatherDao)
    :ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi

    private val _weatherResult = MutableStateFlow<NetworkResponse<WeatherModel>>(NetworkResponse.Loading)
    val weatherResult: StateFlow<NetworkResponse<WeatherModel>> = _weatherResult

    private val _isCityFavorite = MutableStateFlow(false)
    val isCityFavorite: StateFlow<Boolean> = _isCityFavorite

    fun getDataById(cityId: Long) {
        viewModelScope.launch {
            try {

                val city = cityRepository.getCityById(cityId)
                if (city != null) {
                    _isCityFavorite.value = city.favorites
                    getData(city.name)
                } else {
                    _weatherResult.value = NetworkResponse.Error("Город не найден")
                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }
    private fun getData(city: String) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(Constant.apiKey, city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        val currentTime = Date()
                        val formattedTime = formatTime(currentTime)
                        val updatedWeatherModel = it.copy(requestTime = formattedTime)
                        _weatherResult.value = NetworkResponse.Success(updatedWeatherModel)
                        saveToCache(updatedWeatherModel, city)
                    }
                } else {

                    loadFromCache(city)

                    if (_weatherResult.value !is NetworkResponse.Success) {
                        _weatherResult.value = NetworkResponse.Error("Нет данных")
                    }
                }
            } catch (e: Exception) {

                loadFromCache(city)

                if (_weatherResult.value !is NetworkResponse.Success) {
                    _weatherResult.value = NetworkResponse.Error("Ошибка сети")
                }
            }
        }
    }
    private fun formatTime(date: Date): String {
        val dateFormat = SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault())
        return dateFormat.format(date)
    }


    private suspend fun saveToCache(model: WeatherModel, city: String) {
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

    private suspend fun loadFromCache(city: String) {
        val cachedData = weatherDao.getWeather(city)
        if (cachedData != null) {
            val forecastList = Gson().fromJson(cachedData.forecastDays, Array<ForecastDay>::class.java).toList()
            val model = WeatherModel(
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
            _weatherResult.value = NetworkResponse.Success(model)
        }
    }
    fun setCityAsFavorite(cityId: Long) {
        viewModelScope.launch {
            cityRepository.updateFavorites(cityId)
            val city = cityRepository.getCityById(cityId)
            city?.let {
                _isCityFavorite.value = it.favorites
            }
        }
    }

}














