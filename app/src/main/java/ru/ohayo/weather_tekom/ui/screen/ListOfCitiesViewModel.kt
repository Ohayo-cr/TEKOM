package ru.ohayo.weather_tekom.ui.screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.ohayo.weather_tekom.data.sources.room.city.CityDbo
import ru.ohayo.weather_tekom.repository.CityRepository
import javax.inject.Inject

@HiltViewModel
class ListOfCitiesViewModel @Inject constructor(
    private val repository: CityRepository
) : ViewModel() {


    private val _cities = MutableStateFlow<List<CityDbo>>(emptyList())
    val cities: StateFlow<List<CityDbo>> = _cities

    init {
        loadCities()
    }

    private fun loadCities() {
        viewModelScope.launch {
            repository.getAllCity().collect { cities ->
                _cities.value = cities
            }
        }
    }
}