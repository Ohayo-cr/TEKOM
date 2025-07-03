package ru.ohayo.weather_tekom.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.ohayo.weather_tekom.domain.api.Constant
import ru.ohayo.weather_tekom.domain.api.NetworkResponse
import ru.ohayo.weather_tekom.domain.api.RetrofitInstance
import ru.ohayo.weather_tekom.domain.api.WeatherModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel@Inject constructor():ViewModel() {

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
                    }
                }else{
                    _weatherResult.value = NetworkResponse.Error("Failed to load data")
                }
            }
            catch (e : Exception){
                _weatherResult.value = NetworkResponse.Error("Failed to load data")
            }

        }
    }

}













