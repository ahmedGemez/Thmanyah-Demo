package com.thmanyah.domain.ext

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class StringExtTest {

    @Test
    fun `getRelativeTimeData should return correct time differences for valid date`() {
        // Given
        val formatter = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneOffset.UTC)
        val now = Instant.now()
        val oneHourAgo = now.minus(1, java.time.temporal.ChronoUnit.HOURS)
        val dateString = formatter.format(oneHourAgo)

        // When
        val result = dateString.getRelativeTimeData()

        // Then
        assert(result != null)
        result?.let {
            assertEquals(60L, it[0]) // minutes
            assertEquals(1L, it[1])  // hours
            assertEquals(0L, it[2])  // days
            assertEquals(0L, it[3])  // months
            assertEquals(0L, it[4])  // years
        }
    }

    @Test
    fun `getRelativeTimeData should return null for invalid date string`() {
        // Given
        val invalidDateString = "invalid-date-string"

        // When
        val result = invalidDateString.getRelativeTimeData()

        // Then
        assertNull(result)
    }

    @Test
    fun `getRelativeTimeData should handle dates from different years`() {
        // Given
        val formatter = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneOffset.UTC)
        val now = ZonedDateTime.now(ZoneOffset.UTC)
        val oneYearAgo = now.minusYears(1)
        val dateString = formatter.format(oneYearAgo)

        // When
        val result = dateString.getRelativeTimeData()

        // Then
        assert(result != null)
        result?.let {
            assertEquals(1L, it[4])  // years
        }
    }

    @Test
    fun `getRelativeTimeData should handle dates from different months`() {
        // Given
        val formatter = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneOffset.UTC)
        val now = ZonedDateTime.now(ZoneOffset.UTC)
        val oneMonthAgo = now.minusMonths(1)
        val dateString = formatter.format(oneMonthAgo)

        // When
        val result = dateString.getRelativeTimeData()

        // Then
        assert(result != null)
        result?.let {
            assertEquals(1L, it[3])  // months
        }
    }

    @Test
    fun `getRelativeTimeData should handle dates from different days`() {
        // Given
        val formatter = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneOffset.UTC)
        val now = Instant.now()
        val oneDayAgo = now.minus(1, java.time.temporal.ChronoUnit.DAYS)
        val dateString = formatter.format(oneDayAgo)

        // When
        val result = dateString.getRelativeTimeData()

        // Then
        assert(result != null)
        result?.let {
            assertEquals(1L, it[2])  // days
        }
    }

    @Test
    fun `getRelativeTimeData should handle dates from different minutes`() {
        // Given
        val formatter = DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneOffset.UTC)
        val now = Instant.now()
        val fiveMinutesAgo = now.minus(5, java.time.temporal.ChronoUnit.MINUTES)
        val dateString = formatter.format(fiveMinutesAgo)

        // When
        val result = dateString.getRelativeTimeData()

        // Then
        assert(result != null)
        result?.let {
            assertEquals(5L, it[0])  // minutes
        }
    }
} 