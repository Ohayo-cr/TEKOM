package ru.ohayo.weather_tekom.ui.screen.cityList


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import ru.ohayo.weather_tekom.data.room.city.CityDbo
import ru.ohayo.weather_tekom.ui.navigation.Screen
import ru.ohayo.weather_tekom.ui.screen.cityList.components.AddCityDialog
import ru.ohayo.weather_tekom.ui.screen.cityList.components.DeleteCityDialog
import ru.ohayo.weather_tekom.ui.theme.AppColor



@Composable
    fun ListOfCitiesScreen(viewModel: ListOfCitiesViewModel = hiltViewModel(),
                           navController: NavController) {

    val cities by viewModel.cities.collectAsState(initial = emptyList())

    val showAddDialog by viewModel.showAddDialog.collectAsState()

    val showDeleteDialog by viewModel.showDeleteDialog.collectAsState()

    val cityAddName by viewModel.addCityName.collectAsState()


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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 80.dp)
                )  {
                    items(cities.sortedBy { it.name }) { city ->
                        CityItem(
                            city = city,
                            isSelected = {
                                navController.navigate(Screen.WeatherRo.createRoute(city.name))
                            },
                            onDeleteClick = {
                                viewModel.confirmDeleteCity(city.name)
                            }
                        )
                    }
                }
                Button(
                    onClick = { viewModel.onAddCityButtonClicked() },
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
    if (showAddDialog) {
        AddCityDialog(
            onDismiss = { viewModel.onDialogDismissed() },
            onConfirm = { viewModel.addNewCity() },
            onTextChange = { viewModel.onCityNameChange(it) },
            cityName = cityAddName
        )
    }
    if (showDeleteDialog != null) {
        DeleteCityDialog(
            cityName = showDeleteDialog ?: "",
            onDismiss = { viewModel.deleteDialogDismissed() },
            onConfirm = {
                viewModel.deleteCityConfirmed()
            }
        )
    }
}

@Composable
fun CityItem(city: CityDbo, isSelected: () -> Unit = {},
             onDeleteClick: () -> Unit = {}) {
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
                color = MaterialTheme.colorScheme.outline
            )

            IconButton(
                modifier = Modifier
                    .size(36.dp),
                onClick = onDeleteClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Delete city",
                    tint = if (isSystemInDarkTheme()) Color.White else Color.DarkGray,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}




