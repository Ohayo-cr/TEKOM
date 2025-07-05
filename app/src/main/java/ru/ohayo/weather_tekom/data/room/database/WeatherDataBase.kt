package ru.ohayo.weather_tekom.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ohayo.weather_tekom.data.room.city.CityDao
import ru.ohayo.weather_tekom.data.room.city.CityDbo
import ru.ohayo.weather_tekom.data.room.weatherCache.WeatherDao
import ru.ohayo.weather_tekom.data.room.weatherCache.WeatherDbo


@Database(entities = [CityDbo::class, WeatherDbo::class], version = 1)

abstract class WeatherDatabase: RoomDatabase() {
    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        const val DATABASE_NAME = "weather_db"
    }
}