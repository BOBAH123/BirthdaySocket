package com.vlesko.features.birthday.presentation.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.vlesko.features.birthday.presentation.R
import com.vlesko.features.birthday.presentation.theme.Colors

enum class AppTheme(
    val key: String,
    @DrawableRes val backgroundImage: Int,
    @DrawableRes val setPhotoImage: Int,
    val borderColor: Color,
    val bgColor: Color,
) {
    ElephantTheme(
        key = "elephant",
        backgroundImage = R.drawable.bg_android_elephant,
        setPhotoImage = R.drawable.ic_camera_yellow,
        borderColor = Colors.DARK_YELLOW,
        bgColor = Colors.LIGHT_YELLOW
    ),

    FoxTheme(
        key = "fox",
        backgroundImage = R.drawable.bg_android_fox,
        setPhotoImage = R.drawable.ic_camera_green,
        borderColor = Colors.DARK_GREEN,
        bgColor = Colors.LIGHT_GREEN
    ),

    PelicanTheme(
        key = "pelican",
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
    TWELVE(R.drawable.ic_number_12);

    companion object {
        fun getNumberModel(age: Int): Numbers = when (age) {
            1 -> ONE
            2 -> TWO
            3 -> THREE
            4 -> FOUR
            5 -> FIVE
            6 -> SIX
            7 -> SEVEN
            8 -> EIGHT
            9 -> NINE
            10 -> TEN
            11 -> ELEVEN
            12 -> TWELVE
            else -> ZERO
        }
    }
}