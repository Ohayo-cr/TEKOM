package ru.ohayo.weather_tekom.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.ohayo.weather_tekom.ui.screen.cityList.ListOfCitiesScreen
import ru.ohayo.weather_tekom.ui.screen.WeatherScreen

@Composable
fun NavHostScreen(navController: NavHostController) {


        NavHost(
            navController = navController,
            startDestination = Screen.CitiesRo.route,
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
            ) { entry ->
                val cityId = entry.arguments?.getLong("cityId") ?: 0L
                WeatherScreen(cityId = cityId, navController = navController)
            }
        }
    }

