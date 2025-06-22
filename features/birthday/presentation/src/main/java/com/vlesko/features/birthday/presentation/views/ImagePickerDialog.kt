package com.vlesko.features.birthday.presentation.views

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.vlesko.features.birthday.presentation.R
import com.vlesko.features.birthday.presentation.models.AppTheme
import com.vlesko.features.birthday.presentation.theme.Fonts
import java.io.File

@Composable
fun ImagePickerDialog(
    appTheme: AppTheme,
    onImageSelected: (Uri) -> Unit,
    onDismiss: () -> Unit
) {
    var tempUri: Uri? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    var showCamera by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showCamera = true
        }
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) tempUri?.let {
                onImageSelected(it)
                onDismiss()
            }
        }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                onImageSelected(it)
                onDismiss()
            }
        }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            showCamera = true
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = true
        )
    ) {
        ImagePickerDialogContent(
            appTheme = appTheme,
            showCamera = showCamera,
            onGalleryClick = { galleryLauncher.launch("image/*") },
            onCameraChange = {
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    context.createTempImageFile()
                )
                tempUri = uri
                cameraLauncher.launch(uri)
            },
        )
    }
}

@Preview
@Composable
private fun ImagePickerDialogContent(
    appTheme: AppTheme = AppTheme.ElephantTheme,
    showCamera: Boolean = true,
    onCameraChange: () -> Unit = {},
    onGalleryClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(appTheme.bgColor)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.Select_Picker_Type_Title),
            style = Fonts.Benton_Primary
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (showCamera) {
                Icon(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .clickable(onClick = onCameraChange),
                    painter = painterResource(R.drawable.ic_camera_24),
                    contentDescription = "Take picture",
                    tint = appTheme.borderColor
                )
            }
            Icon(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onGalleryClick),
                painter = painterResource(R.drawable.ic_gallery_24),
                contentDescription = "Select from gallery",
                tint = appTheme.borderColor
            )
        }
    }
}

private fun Context.createTempImageFile(): File {
    val dir = File(cacheDir, "images").apply { mkdirs() }
    return File.createTempFile("photo_", ".jpg", dir)
}