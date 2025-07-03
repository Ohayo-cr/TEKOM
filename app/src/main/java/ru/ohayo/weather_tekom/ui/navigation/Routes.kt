package ru.ohayo.weather_tekom.ui.navigation

sealed class Screen(val route: String) {
    object CitiesRo : Screen("cities_screen")
    object WeatherRo : Screen("weather_screen")
}