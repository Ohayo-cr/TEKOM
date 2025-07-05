package ru.ohayo.weather_tekom.data.room.weatherCache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: WeatherDbo)

    @Query("SELECT * FROM weather_cache WHERE cityName = :cityName")
    suspend fun getWeather(cityName: String): WeatherDbo?

    @Query("DELETE FROM weather_cache")
    suspend fun clearCache()
}
