package ru.ohayo.weather_tekom.ui.screen.weather.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.materii.pullrefresh.PullRefreshIndicator
import dev.materii.pullrefresh.PullRefreshLayout
import dev.materii.pullrefresh.PullRefreshState
import ru.ohayo.weather_tekom.ui.theme.AppColor

@Composable
fun PullRefreshSample(
    flipped: Boolean,
    pullRefreshState: PullRefreshState,
    modifier: Modifier = Modifier
) {
    PullRefreshLayout(
        modifier = modifier,
        state = pullRefreshState,
        flipped = flipped,
        indicator = {
            PullRefreshIndicator(
                state = pullRefreshState,
                flipped = flipped,
                backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                contentColor = AppColor
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {

        }
    }
}