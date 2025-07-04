package ru.ohayo.weather_tekom.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.ohayo.weather_tekom.R
import ru.ohayo.weather_tekom.data.sources.room.city.CityDbo
import ru.ohayo.weather_tekom.ui.navigation.Screen
import ru.ohayo.weather_tekom.ui.theme.AppColor


@Composable
    fun ListOfCitiesScreen(viewModel: ListOfCitiesViewModel = hiltViewModel(),
                           navController: NavController) {

    val cities by viewModel.cities.collectAsState()


        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
            if (cities.isEmpty()) {
                Text(
                    text = "Нет городов",
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn {
                    items(cities.sortedBy { it.name }) { city ->
                        CityItem(
                            city = city,
                            isSelected = {
                                navController.navigate(Screen.WeatherRo.createRoute(city.name))
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColor),
                shape = RoundedCornerShape(12.dp)

            ) {
                Text(text = "Добавить город",modifier = Modifier,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,)

            }
        }
    }

@Composable
fun CityItem(city: CityDbo, isSelected: () -> Unit = {}) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)
        .clickable(onClick = isSelected),
        colors = CardDefaults.cardColors(
            containerColor = AppColor.copy(alpha = 0.3f))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 10.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = city.name,
                modifier = Modifier.weight(1f),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
            )

            IconButton(
                modifier = Modifier
                    .size(36.dp),
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Delete city",
                    tint = Color.DarkGray,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}




