package com.vlesko.features.birthday.data.di

import com.vlesko.features.birthday.data.repository.WebSocketRepositoryImpl
import com.vlesko.features.birthday.data.socket.OkHttpWebSocketService
import com.vlesko.features.birthday.domain.repository.repository.WebSocketRepository
import com.vlesko.features.birthday.domain.repository.usecases.CreateSocketConnectionUseCase
import com.vlesko.features.birthday.domain.repository.usecases.ObserveWebSocketMessagesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BirthdayDiModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient()

    @Provides
    fun provideWebSocketRepository(service: OkHttpWebSocketService): WebSocketRepository =
        WebSocketRepositoryImpl(service)

    @Provides
    fun provideCreateSocketConnectionUseCase(repository: WebSocketRepository): CreateSocketConnectionUseCase =
        CreateSocketConnectionUseCase(repository)

    @Provides
    fun provideObserveWebSocketMessagesUseCase(repository: WebSocketRepository): ObserveWebSocketMessagesUseCase =
        ObserveWebSocketMessagesUseCase(repository)
}