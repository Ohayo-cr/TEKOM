package ru.ohayo.weather_tekom.ui.screen.weather.component

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun String.formatDateToRelative(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))
    val fullDateFormat = SimpleDateFormat("d MMMM, EEEE", Locale("ru"))

    try {
        val date = inputFormat.parse(this) ?: return this
        val calendar = Calendar.getInstance()

        val today = calendar.time
        val tomorrowCalendar = calendar.clone() as Calendar
        tomorrowCalendar.add(Calendar.DAY_OF_MONTH, 1)
        val tomorrow = tomorrowCalendar.time

        val dayAfterTomorrowCalendar = calendar.clone() as Calendar
        dayAfterTomorrowCalendar.add(Calendar.DAY_OF_MONTH, 2)
        val dayAfterTomorrow = dayAfterTomorrowCalendar.time

        return when {
            date.time == today.time -> "Сегодня"
            date.time == tomorrow.time -> "Завтра"
            date.time == dayAfterTomorrow.time -> "Послезавтра"
            else -> fullDateFormat.format(date)
        }
    } catch (e: Exception) {
        return this
    }
}