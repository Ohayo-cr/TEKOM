package ru.ohayo.weather_tekom.ui.screen.cityList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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

    val cities: Flow<List<CityDbo>> = repository.getAllCity()

    // Состояние открытия диалога
    private val _showAddDialog = MutableStateFlow(false)
    val showAddDialog: StateFlow<Boolean> get() = _showAddDialog

    // Состояние вводимого города
    private val _cityName = MutableStateFlow("")
    val cityName: StateFlow<String> get() = _cityName

    fun onAddCityButtonClicked() {
        _showAddDialog.value = true
    }

    fun onDialogDismissed() {
        _showAddDialog.value = false
    }

    fun onCityNameChange(name: String) {
        _cityName.value = name
    }
    fun addNewCity() {
        if (_cityName.value.isNotBlank()) {
            viewModelScope.launch {
                repository.insertSingleCity(CityDbo(name = _cityName.value))
                _cityName.value = ""
                _showAddDialog.value = false
            }
        }
    }
    // Состояние диалога удаления
    private val _cityToDelete = MutableStateFlow<String?>(null)
    val showDeleteDialog: StateFlow<String?> get() = _cityToDelete

    // Открытие диалога удаления
    fun confirmDeleteCity(cityName: String) {
        _cityToDelete.value = cityName
    }

    // Отмена удаления
    fun deleteDialogDismissed() {
        _cityToDelete.value = null
    }
    // Подтверждение удаления
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
