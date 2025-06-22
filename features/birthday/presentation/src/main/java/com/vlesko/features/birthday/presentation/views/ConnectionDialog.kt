package com.vlesko.features.birthday.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vlesko.features.birthday.presentation.R
import com.vlesko.features.birthday.presentation.theme.Colors

@Composable
fun ConnectionDialog(
    ip: String,
    port: String,
    onIpChange: (String) -> Unit,
    onPortChange: (String) -> Unit,
    startConnection: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = true
        )
    ) {
        ConnectionDialogContent(
            ip = ip,
            port = port,
            onIpChange = onIpChange,
            onPortChange = onPortChange,
            startConnection = {
                startConnection()
                onDismiss()
            }
        )
    }
}

@Preview
@Composable
private fun ConnectionDialogContent(
    ip: String = "11.2.3.44",
    port: String = "8080",
    onIpChange: (String) -> Unit = {},
    onPortChange: (String) -> Unit = {},
    startConnection: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Colors.DARK_GREEN)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.Add_Server_Ip_Title)
        )
        BasicTextField(
            modifier = Modifier.padding(top = 6.dp),
            value = ip,
            onValueChange = onIpChange,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(Colors.LIGHT_GREEN)
                        .padding(10.dp)
                ) {
                    innerTextField.invoke()
                }
            }
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(R.string.Add_Server_Port_Title)
        )
        BasicTextField(
            modifier = Modifier.padding(top = 6.dp),
            value = port,
            onValueChange = onPortChange,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(Colors.LIGHT_GREEN)
                        .padding(10.dp)
                ) {
                    innerTextField.invoke()
                }
            }
        )
        Button(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 10.dp),
            onClick = startConnection,
            colors = ButtonDefaults.buttonColors(
                containerColor = Colors.LIGHT_GREEN,
                contentColor = Color.Black,
            )
        ) {
            Text(
                text = stringResource(R.string.Connect_Button_Title)
            )
        }
    }
}