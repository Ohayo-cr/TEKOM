package ru.ohayo.weather_tekom.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.ohayo.weather_tekom.ui.navigation.NavHostScreen
import ru.ohayo.weather_tekom.ui.theme.Weather_TEKOMTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Weather_TEKOMTheme {
                val mainViewModel: MainViewModel = hiltViewModel()
                val navController = rememberNavController()
                NavHostScreen(navController = navController, mainViewModel = mainViewModel)

                }
            }
        }
    }


