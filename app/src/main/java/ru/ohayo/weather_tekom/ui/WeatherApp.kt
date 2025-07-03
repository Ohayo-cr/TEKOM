package ru.ohayo.weather_tekom.ui

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ru.ohayo.weather_tekom.domain.models.DefaultCity
import ru.ohayo.weather_tekom.repository.CityRepository
import javax.inject.Inject


@HiltAndroidApp
class WeatherApp : Application() {
    @Inject
    lateinit var cityRepository: CityRepository
    override fun onCreate() {
        super.onCreate()

        MainScope().launch {
            if (cityRepository.cityIsEmpty()) {
                cityRepository.insertAllCity(DefaultCity.DEFAULT_CITY)
            }

        }
    }
}

