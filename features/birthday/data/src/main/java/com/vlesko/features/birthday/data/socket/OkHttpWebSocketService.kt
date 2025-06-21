package com.vlesko.features.birthday.data.socket

import android.util.Log
import com.vlesko.features.birthday.domain.repository.model.SocketRemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OkHttpWebSocketService @Inject constructor(
    private val client: OkHttpClient
) : WebSocketListener() {

    private var webSocket: WebSocket? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _messageFlow = MutableSharedFlow<SocketRemoteMessage>()
    val messageFlow = _messageFlow.asSharedFlow()

    fun connect(host: String) {
        val request = Request.Builder().url(host).build()
        webSocket = client.newWebSocket(request, this)
    }

    fun disconnect() {
        webSocket?.close(1000, "Client disconnected")
        webSocket = null
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        coroutineScope.launch {
            try {
                val message = Json.decodeFromString<SocketRemoteMessage>(text)
                _messageFlow.emit(message)
            } catch (e: Exception) {
                // Handle or log JSON parse failure
                e.printStackTrace()
            }
        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.d("WebSocket", "WebSocket error: ${t.message}")
        disconnect()
    }
}