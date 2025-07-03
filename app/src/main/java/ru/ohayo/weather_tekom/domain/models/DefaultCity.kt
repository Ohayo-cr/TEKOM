package ru.ohayo.weather_tekom.domain.models

import ru.ohayo.weather_tekom.data.sources.room.city.CityDbo


object DefaultCity {
    val DEFAULT_CITY = listOf(
        "Nizhnii Novgorod",
        "Arzamas",
        "Bor",
        "Dzerzhinsk",
        "Kstovo",
        "Navashino",
        "Pavlovo",
        "Semyonov",
        "Chkalovsk"
    ).map { CityDbo(it) }
}