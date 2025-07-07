package ru.ohayo.weather_tekom.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ohayo.weather_tekom.repository.CityRepository
import ru.ohayo.weather_tekom.ui.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cityRepository: CityRepository) : ViewModel() {

    private val _startDestination = MutableStateFlow(Screen.CitiesRo.route)
    val startDestination: StateFlow<String> get() = _startDestination.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            val cityId = cityRepository.getFavoriteCity()
            if (cityId != null) {
                _startDestination.value = Screen.WeatherRo.createRoute(cityId)
            } else {
                _startDestination.value = Screen.CitiesRo.route
            }
            _isLoading.value = false
        }
    }
}


