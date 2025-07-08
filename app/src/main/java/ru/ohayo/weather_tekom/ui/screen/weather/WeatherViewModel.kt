package ru.ohayo.weather_tekom.ui.screen.weather

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ohayo.weather_tekom.data.remote.api.NetworkResponse
import ru.ohayo.weather_tekom.data.remote.models.WeatherModel
import ru.ohayo.weather_tekom.repository.CityRepository
import ru.ohayo.weather_tekom.repository.WeatherRepository
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel@Inject constructor(
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository,
    private val savedStateHandle: SavedStateHandle
)
    :ViewModel() {


    private val _cityId = MutableStateFlow(savedStateHandle.get<Long>("cityId") ?: 0L)
    val cityId: StateFlow<Long> get() = _cityId.asStateFlow()

    private val _weatherResult = MutableStateFlow<NetworkResponse<WeatherModel>>(NetworkResponse.Loading)
    val weatherResult: StateFlow<NetworkResponse<WeatherModel>> = _weatherResult

    private val _isCityFavorite = MutableStateFlow(false)
    val isCityFavorite: StateFlow<Boolean> = _isCityFavorite

    private val _cityName = MutableStateFlow("")
    val cityName: StateFlow<String> = _cityName

    init {
        viewModelScope.launch {
            if (_cityId.value == 0L) {
                val favoriteCityId = cityRepository.getFavoriteCity()
                if (favoriteCityId != null) {
                    _cityId.value = favoriteCityId
                }
            }

            _cityId.collect { id ->
                if (id != 0L) {
                    getDataById(id)
                }
            }
        }
    }

    fun getDataById(cityId: Long) {
        viewModelScope.launch {
            try {
                val city = cityRepository.getCityById(cityId)
                if (city != null) {
                    _isCityFavorite.value = city.favorites
                    _cityName.value = city.name
                    getData(city.name)
                } else {
                    _weatherResult.value = NetworkResponse.Error("Город не найден $cityId")
                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }

    private fun getData(city: String) {
        if (_weatherResult.value !is NetworkResponse.Success) {
            _weatherResult.value = NetworkResponse.Loading
        }

        viewModelScope.launch {
            val result = weatherRepository.getWeatherData(city)
            _weatherResult.value = result
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














