package com.vlesko.birthdaywebsocket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vlesko.birthdaywebsocket.ui.theme.BirthdayWebSocketTheme
import com.vlesko.features.birthday.presentation.BirthdayDetailsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BirthdayWebSocketTheme {
                BirthdayDetailsScreen()
            }
        }
    }
}