package com.example.quiznotion.data.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.toFormattedDateTimeString(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.date} ${
        localDateTime.time.hour.toString().padStart(2, '0')
    }:${
        localDateTime.time.minute.toString().padStart(2, '0')
    }:${localDateTime.time.second.toString().padStart(2, '0')}"
}