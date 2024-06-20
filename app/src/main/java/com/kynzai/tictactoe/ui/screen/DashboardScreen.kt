package com.kynzai.tictactoe.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kynzai.tictactoe.R
import com.kynzai.tictactoe.data.GameMode
import com.kynzai.tictactoe.ui.component.GameModeSelector
import com.kynzai.tictactoe.ui.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel
) {

    val context = LocalContext.current

    val playButtonEnabled = remember(viewModel.bluetoothAvailable, viewModel.selectedGameMode) {
        if (!viewModel.bluetoothAvailable && viewModel.selectedGameMode == GameMode.PvPBluetooth) {
            return@remember false
        }

        return@remember true
    }

    LaunchedEffect(context) {
        viewModel.checkBluetooth(context)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        // TODO: Заменить картинку на свою(сделать в фотошопе)
        Image(
            painter = painterResource(id = R.drawable.ic_tic_tac_toe_o),
            contentDescription = null,
            modifier = Modifier
                .size(96.dp)
                .clip(MaterialTheme.shapes.large)
        )

        Spacer(modifier = Modifier.padding(2.dp))

        GameModeSelector(
            gameModes = GameMode.values(),
            onGameModeChanged = viewModel::updateGameMode,
            navController = navController,
            modifier = Modifier
                .fillMaxWidth(0.6f)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    context.startActivity(
                        Intent(Intent.ACTION_VIEW).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            data = Uri.parse("https://t.me/share_tea085")
                        }
                    )
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_github_mark),
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.padding(2.dp))

        OpenSourceText()
    }
}

@Composable
fun OpenSourceText() {

    val context = LocalContext.current

    val openSourceProjectMsg = buildAnnotatedString {
        withStyle(
            style = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light
            ).toSpanStyle()
        ) {
            append(" Автор проекта по ссылке")

            pushStringAnnotation(tag = "telegram", annotation = "https://t.me/share_tea085")

            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append(" Telegram")
            }

            pop()

            append(" или нажав на значок telegram выше")
        }
    }

    ClickableText(
        text = openSourceProjectMsg,
        style = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center
        ),
        onClick = { offset ->
            openSourceProjectMsg.getStringAnnotations(
                tag = "telegram",
                start = offset,
                end = offset
            ).firstOrNull()?.let { _ ->
                context.startActivity(
                    Intent(Intent.ACTION_VIEW).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        data = Uri.parse("https://t.me/share_tea085")
                    }
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .padding(8.dp)
    )
}
