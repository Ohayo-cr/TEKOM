package ru.ohayo.weather_tekom.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import dev.materii.pullrefresh.rememberPullRefreshState
import ru.ohayo.weather_tekom.data.remote.api.NetworkResponse
import ru.ohayo.weather_tekom.data.remote.models.ForecastDay
import ru.ohayo.weather_tekom.data.remote.models.WeatherModel
import ru.ohayo.weather_tekom.ui.navigation.Screen
import ru.ohayo.weather_tekom.ui.theme.AppColor



@Composable
fun WeatherScreen(viewModel: WeatherViewModel= hiltViewModel(), cityId: Long,
                  navController: NavHostController
) {

    LaunchedEffect(cityId) {
        if (cityId != 0L) {
            viewModel.getDataById(cityId)
        }
    }
    val isCityFavorite by viewModel.isCityFavorite.collectAsState()
    val weatherResult by viewModel.weatherResult.collectAsState()

    var isRefreshing by remember {
        mutableStateOf(false)
    }
    var pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.getDataById(cityId) })
    var flipped by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)

        ) {
            PullRefreshSample( flipped = flipped,
                pullRefreshState = pullRefreshState,
                modifier = Modifier.fillMaxSize())

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                when (val result = weatherResult) {
                    is NetworkResponse.Error -> {
                        Text(
                            text = result.message,
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }

                    NetworkResponse.Loading -> {
                        CircularProgressIndicator()
                    }

                    is NetworkResponse.Success -> {
                        WeatherDetails(
                            data = result.data,
                            key = result.data.hashCode(),
                            clickBottom = { navController.navigate(Screen.CitiesRo.route) },
                            clickImHere = { viewModel.setCityAsFavorite(cityId) },
                            isCityFavorite = isCityFavorite
                        )

                    }

                    else -> {}
                }

            }
        }
    }




@Composable
fun WeatherDetails(data: WeatherModel,key: Any, clickBottom: () -> Unit,clickImHere: () -> Unit,
                   isCityFavorite: Boolean) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Text(text = data.location.name, fontSize = 24.sp,
                color = MaterialTheme.colorScheme.outline)
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier
                .padding(horizontal = 4.dp)
                .background(if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray, shape = RoundedCornerShape(10.dp))
                .clickable(onClick = clickImHere)) {
                Row(modifier = Modifier.padding(end = 4.dp), verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location icon",
                        modifier = Modifier.size(40.dp),
                        tint =if (isCityFavorite) AppColor  else MaterialTheme.colorScheme.outline
                    )
                    Text( text = if (isCityFavorite) "Вы тут" else "Я тут",
                        color = MaterialTheme.colorScheme.outline,
                        fontSize = 14.sp,
                        modifier = Modifier.width(50.dp),
                        textAlign = TextAlign.Center
                        )
                }

            }

        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = " ${data.current.temp_c} ° c",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline
        )

        AsyncImage(
            modifier = Modifier.size(120.dp),
            model = "https:${data.current.condition.icon}".replace("64x64","128x128"),
            contentDescription = "Condition icon"
        )
        Text(
            text = data.current.condition.text,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("Влажность",data.current.humidity)
                    WeatherKeyVal("Скорость ветра",data.current.wind_kph+" км/ч")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("Последнее обновление", data.requestTime)
                }
            }
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween) {


            Card {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    data.forecast.forecastday.forEachIndexed { index, forecastDay ->
                        ForecastItem(forecastDay)
                        if (index < data.forecast.forecastday.size - 1) {
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                thickness = 1.dp,
                                color = Color.LightGray.copy(alpha = 0.3f)
                            )
                        }
                    }
                }
            }

            TextButton(
                onClick = clickBottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                ),
                shape = RoundedCornerShape(12.dp)

            ) {
                Text(
                    text = "К списку городов", modifier = Modifier,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                )

            }
        }

    }
}


@Composable
fun WeatherKeyVal(key : String, value : String) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.outline)
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color.Gray)
    }
}
@Composable
fun ForecastItem(dayData: ForecastDay) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {

            Text(
                text = dayData.date.formatDateToRelative(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 4.dp),
            )


            Row {
                Text(
                    text = "${dayData.day.maxtemp_c.toInt()}°C",
                    fontSize = 20.sp,
                    modifier = Modifier.width(60.dp),
                    textAlign = TextAlign.Center
                )
                AsyncImage(
                    model = "https:${dayData.day.condition.icon}".replace("64x64", "128x128"),
                    contentDescription = null,
                    modifier = Modifier
                        .size(26.dp)
                        .padding(horizontal = 2.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "${dayData.day.mintemp_c.toInt().toString().padStart(2, ' ')}°C",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    modifier = Modifier.width(60.dp),
                    textAlign = TextAlign.Center

                )

            }
        }
    }

