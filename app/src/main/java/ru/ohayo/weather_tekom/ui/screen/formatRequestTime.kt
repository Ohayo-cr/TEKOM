package ru.ohayo.weather_tekom.ui.screen

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

fun formatRequestTime(instant: Instant): String {
    val zoneId = ZoneId.systemDefault()
    val localDateTime = instant.atZone(zoneId).toLocalDateTime()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss")
    return localDateTime.format(formatter)
}