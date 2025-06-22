package com.vlesko.features.birthday.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.vlesko.features.birthday.presentation.models.Numbers
import com.vlesko.features.birthday.presentation.theme.Fonts.Benton_Primary
import com.vlesko.features.birthday.presentation.viewModel.BirthdayDetailsViewModel
import com.vlesko.features.birthday.presentation.viewModel.BirthdayDetailsViewModelState
import com.vlesko.features.birthday.presentation.views.BabyImageContainer
import com.vlesko.features.birthday.presentation.views.ConnectionDialog

@Composable
fun BirthdayDetailsScreen(
    viewModel: BirthdayDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    if (state.showConnectionDialog) {
        ConnectionDialog(
            ip = state.ip.orEmpty(),
            port = state.port,
            onIpChange = viewModel::onIpChanged,
            onPortChange = viewModel::onPortChanged,
            startConnection = viewModel::connectToServer,
            onDismiss = viewModel::hideConnectionDialog
        )
    }

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
            .background(state.appTheme.bgColor.copy(alpha = 0.9f))
    ) {
        val guidelineBottom = createGuidelineFromBottom(0.16f)
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
            modifier = Modifier
                .constrainAs(theme) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            painter = painterResource(state.appTheme.backgroundImage),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Image(
            modifier = Modifier
                .constrainAs(logo) {
                    bottom.linkTo(guidelineBottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(29.45.dp),
            painter = painterResource(R.drawable.nanit_logo),
            contentDescription = null
        )
    }
}