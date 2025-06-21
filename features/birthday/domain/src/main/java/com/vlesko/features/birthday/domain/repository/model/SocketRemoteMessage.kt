package com.vlesko.features.birthday.domain.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class SocketRemoteMessage(
    val name: String,
    val dob: Long,
    val theme: String
)