package com.vlesko.features.birthday.data.repository

import com.vlesko.features.birthday.data.socket.OkHttpWebSocketService
import com.vlesko.features.birthday.domain.repository.model.SocketRemoteMessage
import com.vlesko.features.birthday.domain.repository.repository.WebSocketRepository
import kotlinx.coroutines.flow.Flow

class WebSocketRepositoryImpl(
    private val service: OkHttpWebSocketService
) : WebSocketRepository {

    override fun connect(host: String) {
        service.connect(host)
    }

    override fun disconnect() {
        service.disconnect()
    }

    override fun observeMessages(): Flow<SocketRemoteMessage> = service.messageFlow
}