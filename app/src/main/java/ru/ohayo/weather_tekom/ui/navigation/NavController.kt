package ru.ohayo.weather_tekom.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.ohayo.weather_tekom.ui.MainViewModel
import ru.ohayo.weather_tekom.ui.screen.cityList.ListOfCitiesScreen
import ru.ohayo.weather_tekom.ui.screen.weather.WeatherScreen

@Composable
fun NavHostScreen(navController: NavHostController, mainViewModel: MainViewModel = hiltViewModel()) {

    val isLoading by mainViewModel.isLoading.collectAsState()
    val startDestination by mainViewModel.startDestination.collectAsState()
    if (isLoading) {

    } else {

        NavHost(
            navController = navController,
            startDestination = startDestination,
            enterTransition = { fadeIn(animationSpec = tween(0)) },
            exitTransition = { fadeOut(animationSpec = tween(0)) },
            popEnterTransition = { fadeIn(animationSpec = tween(0)) },
            popExitTransition = { fadeOut(animationSpec = tween(0)) }
        ) {
            composable(Screen.CitiesRo.route) {
                ListOfCitiesScreen(navController = navController)
            }

            composable(
                Screen.WeatherRo.route,
                arguments = listOf(navArgument("cityId") { type = NavType.LongType })
            ) {
                WeatherScreen(navController = navController)
            }
        }
    }
}

