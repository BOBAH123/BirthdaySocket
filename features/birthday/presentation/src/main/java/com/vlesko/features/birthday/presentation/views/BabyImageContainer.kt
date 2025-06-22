package com.vlesko.features.birthday.presentation.views

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.vlesko.features.birthday.presentation.R
import com.vlesko.features.birthday.presentation.models.AppTheme
import kotlin.math.sqrt

@Preview
@Composable
fun BabyImageContainer(
    modifier: Modifier = Modifier,
    appTheme: AppTheme = AppTheme.FoxTheme,
    babyImageUri: Uri? = null,
    onAddPhotoClick: () -> Unit = {}
) {
    val radiusOffset = remember { 100.dp / sqrt(2f) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        babyImageUri?.let { uri ->
            Image(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(appTheme.bgColor)
                    .border(color = appTheme.borderColor, shape = CircleShape, width = 7.dp),
                painter = rememberAsyncImagePainter(uri),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        } ?: Icon(
            modifier = Modifier
                .clip(CircleShape)
                .background(appTheme.bgColor)
                .border(color = appTheme.borderColor, shape = CircleShape, width = 7.dp)
                .padding(45.dp),
            painter = painterResource(R.drawable.ic_happy_face),
            contentDescription = null,
            tint = appTheme.borderColor
        )

        Box(
            modifier = Modifier
                .offset(
                    x = radiusOffset,
                    y = -radiusOffset
                )
                .align(Alignment.Center)
                .clip(CircleShape)
                .clickable(onClick = onAddPhotoClick),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(appTheme.setPhotoImage),
                contentDescription = "Add Photo",
                modifier = Modifier.size(36.dp)
            )
        }
    }
}