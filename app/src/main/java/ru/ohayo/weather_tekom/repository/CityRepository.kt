package ru.ohayo.weather_tekom.repository

import ru.ohayo.weather_tekom.data.sources.room.city.CityDao
import ru.ohayo.weather_tekom.data.sources.room.city.CityDbo
import javax.inject.Inject


class CityRepository @Inject constructor(
    private val cityDao: CityDao
) {
    suspend fun getAllCity(): List<CityDbo> {
        return cityDao.getAllCity()
    }

    suspend fun cityIsEmpty(): Boolean {
        return cityDao.isEmpty()
    }
    suspend fun insertAllCity(city: List<CityDbo>) {
        cityDao.insertAllCity(city)
    }


}