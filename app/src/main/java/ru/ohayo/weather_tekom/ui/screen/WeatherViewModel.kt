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
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel@Inject constructor(
    private val weatherDao: WeatherDao)
    :ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi


    private val _weatherResult = MutableStateFlow<NetworkResponse<WeatherModel>>(NetworkResponse.Loading)
    val weatherResult: StateFlow<NetworkResponse<WeatherModel>> = _weatherResult



    fun getData(city: String) {
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try{
                val response = weatherApi.getWeather(Constant.apiKey,city)
                if(response.isSuccessful){
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                        saveToCache(it, city)
                    }
                }else{
                    _weatherResult.value = NetworkResponse.Error("Нет данных")

                }
            }
            catch (e : Exception){

                _weatherResult.value = NetworkResponse.Error("Ошибка сети")
                loadFromCache(city)
            }


        }
    }

    private suspend fun saveToCache(model: WeatherModel, city: String) {
        val forecastJson = Gson().toJson(model.forecast.forecastday)
        val dbo = WeatherDbo(
            cityName = city,
            tempC = model.current.temp_c,
            humidity = model.current.humidity,
            windKph = model.current.wind_kph,
            conditionText = model.current.condition.text,
            lastUpdated = model.current.last_updated,
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
                    condition = Condition(text = cachedData.conditionText, icon = "", code = ""),
                    last_updated = cachedData.lastUpdated
                ),
                forecast = Forecast(forecastday = forecastList)
            )
            _weatherResult.value = NetworkResponse.Success(model)
        }
    }
}













