package com.vlesko.features.birthday.domain.repository.usecases

import com.vlesko.features.birthday.domain.repository.model.BabyModel
import com.vlesko.features.birthday.domain.repository.model.toBabyModel
import com.vlesko.features.birthday.domain.repository.repository.WebSocketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveWebSocketMessagesUseCase(
    private val repository: WebSocketRepository
) {
    operator fun invoke(): Flow<BabyModel> = repository.observeMessages().map {
        it.toBabyModel()
    }
}