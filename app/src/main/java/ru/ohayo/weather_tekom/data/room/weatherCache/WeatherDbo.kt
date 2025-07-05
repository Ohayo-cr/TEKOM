package ru.ohayo.weather_tekom.data.room.weatherCache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherDbo(
    @PrimaryKey
    @ColumnInfo(name = "c")
    val code: String,
    val amount: Double = 0.0
)