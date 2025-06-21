package com.vlesko.features.birthday.domain.repository.repository

import com.vlesko.features.birthday.domain.repository.model.SocketRemoteMessage
import kotlinx.coroutines.flow.Flow

interface WebSocketRepository {
    fun connect(host: String)
    fun observeMessages(): Flow<SocketRemoteMessage>
    fun disconnect()
}