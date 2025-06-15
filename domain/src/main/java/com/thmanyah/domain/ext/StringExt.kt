package com.thmanyah.domain.ext

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun String.getRelativeTimeData(): List<Long>? {
    return try {
        val formatter = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneOffset.UTC)
        val dateTime = Instant.from(formatter.parse(this))
        val now = Instant.now()
        val minutes = ChronoUnit.MINUTES.between(dateTime, now)
        val hours = ChronoUnit.HOURS.between(dateTime, now)
        val days = ChronoUnit.DAYS.between(dateTime, now)
        val months =
            ChronoUnit.MONTHS.between(dateTime.atZone(ZoneOffset.UTC), now.atZone(ZoneOffset.UTC))
        val years =
            ChronoUnit.YEARS.between(dateTime.atZone(ZoneOffset.UTC), now.atZone(ZoneOffset.UTC))
        listOf(minutes, hours, days, months, years)
    } catch (e: Exception) {
        null
    }
}