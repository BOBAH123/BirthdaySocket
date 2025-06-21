package com.vlesko.features.birthday.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vlesko.features.birthday.presentation.theme.Colors
import com.vlesko.features.birthday.presentation.theme.Fonts.Benton_Primary
import com.vlesko.features.birthday.presentation.viewModel.BirthdayDetailsViewModel
import com.vlesko.features.birthday.presentation.viewModel.BirthdayDetailsViewModelState
import kotlin.math.sqrt

@Composable
fun BirthdayDetailsScreen(
    viewModel: BirthdayDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    BirthdayDetailsScreenContent(state)
}

@Preview
@Composable
private fun BirthdayDetailsScreenContent(
    state: BirthdayDetailsViewModelState = BirthdayDetailsViewModelState()
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(state.appTheme.bgColor)
    ) {

        val guidelineBottom = createGuidelineFromBottom(0.2f)
        val (titleContent, imageBlock, logo, theme) = createRefs()

        Column(
            Modifier
                .constrainAs(titleContent) {
                    top.linkTo(parent.top)
                    bottom.linkTo(imageBlock.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.Main_Title).format(
                    state.name?.uppercase()
                ),
                textAlign = TextAlign.Center,
                style = Benton_Primary
            )

            Row(
                modifier = Modifier.padding(top = 13.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_left_swirls),
                    contentDescription = null,
                )
                Image(
                    modifier = Modifier.padding(horizontal = 22.dp),
                    painter = painterResource(state.age?.numberIcon ?: Numbers.ZERO.numberIcon),
                    contentDescription = state.age?.name,
                )
                Icon(
                    painter = painterResource(R.drawable.ic_right_swirls),
                    contentDescription = null,
                )
            }

            Text(
                modifier = Modifier.padding(top = 14.dp),
                text = stringResource(
                    if (state.isYoungerThanAYear) {
                        R.string.Main_SubTitle_Month
                    } else {
                        R.string.Main_SubTitle_Years
                    }
                ),
                textAlign = TextAlign.Center,
                style = Benton_Primary
            )
        }
        BabyImageContainer(
            modifier = Modifier
                .constrainAs(imageBlock) {
                    bottom.linkTo(logo.top, 15.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 50.dp)
                .padding(top = 15.dp),
            appTheme = state.appTheme
        )
        Image(
            modifier = Modifier.constrainAs(logo) {
                bottom.linkTo(guidelineBottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = null
        )

        Image(
            modifier = Modifier.constrainAs(theme) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            painter = painterResource(state.appTheme.backgroundImage),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview
@Composable
private fun BabyImageContainer(
    modifier: Modifier = Modifier, appTheme: AppTheme = AppTheme.FoxTheme,
) {
    val radiusOffset = remember { 100.dp / sqrt(2f) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
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
                .clickable { },
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

enum class AppTheme(
    @DrawableRes val backgroundImage: Int,
    @DrawableRes val setPhotoImage: Int,
    val borderColor: Color,
    val bgColor: Color,
) {
    ElephantTheme(
        backgroundImage = R.drawable.bg_android_elephant,
        setPhotoImage = R.drawable.ic_camera_yellow,
        borderColor = Colors.DARK_YELLOW,
        bgColor = Colors.LIGHT_YELLOW
    ),

    FoxTheme(
        backgroundImage = R.drawable.bg_android_fox,
        setPhotoImage = R.drawable.ic_camera_green,
        borderColor = Colors.DARK_GREEN,
        bgColor = Colors.LIGHT_GREEN
    ),

    PelicanTheme(
        backgroundImage = R.drawable.bg_android_pelican,
        setPhotoImage = R.drawable.ic_camera_blue,
        borderColor = Colors.DARK_BLUE,
        bgColor = Colors.LIGHT_BLUE
    )
}

enum class Numbers(
    @DrawableRes val numberIcon: Int
) {
    ZERO(R.drawable.ic_number_0),
    ONE(R.drawable.ic_number_1),
    TWO(R.drawable.ic_number_2),
    THREE(R.drawable.ic_number_3),
    FOUR(R.drawable.ic_number_4),
    FIVE(R.drawable.ic_number_5),
    SIX(R.drawable.ic_number_6),
    SEVEN(R.drawable.ic_number_7),
    EIGHT(R.drawable.ic_number_8),
    NINE(R.drawable.ic_number_9),
    TEN(R.drawable.ic_number_10),
    ELEVEN(R.drawable.ic_number_11),
    TWELVE(R.drawable.ic_number_12)
}