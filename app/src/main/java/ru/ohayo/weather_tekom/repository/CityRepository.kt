package ru.ohayo.weather_tekom.repository

import kotlinx.coroutines.flow.Flow
import ru.ohayo.weather_tekom.data.room.city.CityDao
import ru.ohayo.weather_tekom.data.room.city.CityDbo
import javax.inject.Inject


class CityRepository @Inject constructor(
    private val cityDao: CityDao
) {
    fun getAllCity(): Flow<List<CityDbo>> {
        return cityDao.getAllCity()
    }

    suspend fun cityIsEmpty(): Boolean {
        return cityDao.isEmpty()
    }
    suspend fun insertSingleCity(city: CityDbo) {
        cityDao.insertSingleCity(city)
    }
    suspend fun insertAllCity(city: List<CityDbo>) {
        cityDao.insertAllCity(city)
    }
    suspend fun deleteCity(cityName: String) {
        cityDao.deleteCity(cityName)
    }
   suspend fun getCityById(cityId: Long): CityDbo? {
        return cityDao.getCityById(cityId)
    }
    suspend fun updateFavorites(cityId: Long) {
        cityDao.updateFavorites(cityId)
    }
    suspend fun getFavoriteCity(): Long? {
        return cityDao.getFavoriteCity()
    }
}
