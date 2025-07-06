package ru.ohayo.weather_tekom.domain.models

import ru.ohayo.weather_tekom.data.room.city.CityDbo


object DefaultCity {
    val DEFAULT_CITY = listOf(
        "Нижний Новгород",
        "Балахна",
        "Арзамас",
        "Городец",
        "Дзержинск",
        "Кстово",
        "Навашино",
        "Павлово",
        "Чкаловск"
    ).map { CityDbo(name = it) }
}