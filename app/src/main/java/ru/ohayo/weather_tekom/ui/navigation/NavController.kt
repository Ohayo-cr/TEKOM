package ru.ohayo.weather_tekom.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
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
fun NavHostScreen(navController: NavHostController) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val isLoading by mainViewModel.isLoading.collectAsState()
    val startDestination by mainViewModel.startDestination.collectAsState()

        if (isLoading) {

        } else {

            NavHost(
                navController = navController,
                startDestination = startDestination,
            ) {
                composable(Screen.CitiesRo.route,
                    enterTransition = { slideFromBottom() },
                    exitTransition = { fadeOutAnimation() },
                    popEnterTransition = { slideFromBottom() },
                    popExitTransition = { fadeOutAnimation() }
                ) {
                    ListOfCitiesScreen(navController = navController)
                }

                composable(
                    Screen.WeatherRo.route,
                    arguments = listOf(navArgument("cityId") { type = NavType.LongType }),
                    enterTransition = { slideFromTop() },
                    exitTransition = { fadeOutAnimation() },
                    popEnterTransition = { slideFromTop() },
                    popExitTransition = { fadeOutAnimation() }
                ) {
                    WeatherScreen(navController = navController)
                }
            }
        }
    }
private fun slideFromBottom(): EnterTransition = slideInVertically(
    initialOffsetY = { it },
    animationSpec = tween(500)
)

private fun slideFromTop(): EnterTransition = slideInVertically(
    initialOffsetY = { -it },
    animationSpec = tween(500)
)

private fun fadeOutAnimation(): ExitTransition = fadeOut(animationSpec = tween(500))


