package ru.ohayo.weather_tekom.data.room.city

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCity(city: List<CityDbo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleCity(city: CityDbo)

    @Query("SELECT * FROM city")
    fun getAllCity(): Flow<List<CityDbo>>

    @Query("SELECT COUNT(*) == 0 FROM city")
    suspend fun isEmpty(): Boolean
    @Query("DELETE FROM city WHERE city_name = :cityName")
    suspend fun deleteCity(cityName: String)

//    @Query("UPDATE city SET favorites = CASE WHEN id = :cityId THEN 1 ELSE 0 END")
//    suspend fun updateFavorites(cityId: Long)
}
