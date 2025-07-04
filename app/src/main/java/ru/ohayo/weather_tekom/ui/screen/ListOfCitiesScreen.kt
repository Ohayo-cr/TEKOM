package ru.ohayo.weather_tekom.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.ohayo.weather_tekom.data.sources.room.city.CityDbo
import ru.ohayo.weather_tekom.ui.theme.AppColor


@Composable
    fun ListOfCitiesScreen(viewModel: ListOfCitiesViewModel = hiltViewModel()) {
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
                        CityItem(city = city)
                    }
                }
            }

            Button(
                onClick = {
                    // Тут можно открыть другой экран или диалог для добавления города
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColor),
                shape = RoundedCornerShape(12.dp)

            ) {
                Text(text = "Добавить город")

            }
        }
    }

@Composable
fun CityItem(city: CityDbo, onMarkMeHere: () -> Unit = {}) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)) {

        Row(
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 10.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = city.name,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium
            )
            Button(
                onClick = {
                    // TODO: Реализовать логику "Я тут"
                    onMarkMeHere()
                },
                colors = ButtonDefaults.buttonColors(containerColor = AppColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Я тут", color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    }
}




