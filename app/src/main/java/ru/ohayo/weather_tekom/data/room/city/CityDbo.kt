package ru.ohayo.weather_tekom.data.room.city

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "city")
data class CityDbo(
    @PrimaryKey
    @ColumnInfo(name = "city_name")
    val name: String,
    val favorites: Boolean = false
)