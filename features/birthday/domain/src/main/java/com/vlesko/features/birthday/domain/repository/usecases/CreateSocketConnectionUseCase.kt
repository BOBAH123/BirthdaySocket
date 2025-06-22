package com.vlesko.features.birthday.domain.repository.usecases

import com.vlesko.features.birthday.domain.repository.repository.WebSocketRepository

class CreateSocketConnectionUseCase(
    private val repository: WebSocketRepository
) {
    operator fun invoke(connection: String) {
        repository.disconnect()
        repository.connect("ws://$connection/nanit")
    }
}