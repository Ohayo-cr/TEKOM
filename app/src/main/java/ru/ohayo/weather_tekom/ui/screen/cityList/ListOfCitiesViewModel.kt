package ru.ohayo.weather_tekom.ui.screen.cityList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.ohayo.weather_tekom.data.room.city.CityDbo
import ru.ohayo.weather_tekom.repository.CityRepository
import javax.inject.Inject

@HiltViewModel
class ListOfCitiesViewModel @Inject constructor(
    private val repository: CityRepository
) : ViewModel() {

    private val _cities = MutableStateFlow<List<CityDbo>>(emptyList())
    val cities: StateFlow<List<CityDbo>> get() = _cities


    private val _showAddDialog = MutableStateFlow(false)
    val showAddDialog: StateFlow<Boolean> get() = _showAddDialog


    private val _addCityName = MutableStateFlow("")
    val addCityName: StateFlow<String> get() = _addCityName

    fun onAddCityButtonClicked() {
        _showAddDialog.value = true
    }

    fun onDialogDismissed() {
        _showAddDialog.value = false
    }

    fun onCityNameChange(name: String) {
        _addCityName.value = name
    }
    fun loadCities() {
        viewModelScope.launch {
            repository.getAllCity().collect { cities ->
                _cities.value = cities
            }
        }
    }

    init {
        loadCities()
    }

    fun addNewCity() {
        if (_addCityName.value.isNotBlank()) {
            viewModelScope.launch {
                repository.insertSingleCity(CityDbo(name = _addCityName.value))
                _addCityName.value = ""
                _showAddDialog.value = false

            }
        }
    }

    private val _cityToDelete = MutableStateFlow<String?>(null)
    val showDeleteDialog: StateFlow<String?> get() = _cityToDelete


    fun confirmDeleteCity(cityName: String) {
        _cityToDelete.value = cityName
    }


    fun deleteDialogDismissed() {
        _cityToDelete.value = null
    }

    fun deleteCityConfirmed() {
        val city = _cityToDelete.value
        if (city != null) {
            viewModelScope.launch {
                repository.deleteCity(city)

            }
        }
        deleteDialogDismissed()
    }

}
