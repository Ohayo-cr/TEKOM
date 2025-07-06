package ru.ohayo.weather_tekom.data.room.city

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "city")
data class CityDbo(
    @ColumnInfo(name = "city_name")
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val favorites: Boolean = false
)