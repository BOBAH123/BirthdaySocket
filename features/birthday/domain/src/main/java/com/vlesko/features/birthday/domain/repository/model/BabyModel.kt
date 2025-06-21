package com.vlesko.features.birthday.domain.repository.model

import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId

data class BabyModel(
    val name: String,
    val theme: String,
    val age: Period
)

fun SocketRemoteMessage.toBabyModel(): BabyModel =
    BabyModel(
        name = name,
        theme = theme,
        age = calculateBabyAge(dob)
    )

fun calculateBabyAge(dobMillis: Long): Period {
    val dobDate = Instant.ofEpochMilli(dobMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val today = LocalDate.now()
    return Period.between(dobDate, today)
}