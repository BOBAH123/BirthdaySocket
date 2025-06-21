package com.vlesko.features.birthday.domain.repository.usecases

import com.vlesko.features.birthday.domain.repository.repository.WebSocketRepository

class CreateSocketConnectionUseCase(
    private val repository: WebSocketRepository
) {
    operator fun invoke(port: String) {
        repository.disconnect()
        repository.connect("ws://$port:8080/nanit")
    }
}