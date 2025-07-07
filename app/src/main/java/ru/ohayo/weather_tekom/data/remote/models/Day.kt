package ru.ohayo.weather_tekom.data.remote.models

data class Day(
    val maxtemp_c: Double,
    val mintemp_c: Double,
    val condition: Condition
)
val DayStub = Day(
    maxtemp_c = 0.0,
    mintemp_c = 0.0,
    condition = ConditionStub
)