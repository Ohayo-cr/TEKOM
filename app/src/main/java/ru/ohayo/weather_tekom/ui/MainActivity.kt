package ru.ohayo.weather_tekom.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
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

                val navController = rememberNavController()
                Box (modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                        ) {
                    NavHostScreen(navController = navController)
                }
            }
        }
    }
}
