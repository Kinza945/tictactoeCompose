package com.kynzai.tictactoe.ui.component

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kynzai.tictactoe.data.GameMode
import com.kynzai.tictactoe.data.TicTacToeScreen
import com.kynzai.tictactoe.model.modeName

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameModeSelector(
	gameModes: Array<GameMode>,
	navController: NavController,
	modifier: Modifier = Modifier,
	onGameModeChanged: (GameMode) -> Unit
) {

	Column(
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = modifier
	) {
		gameModes.forEach { gameMode ->
			Button(
				onClick = {
					onGameModeChanged(gameMode)
					navController.navigate(
						TicTacToeScreen.Game.Home.createRoute(
							gameMode = gameMode)
					)
						  },
				modifier = Modifier
					.padding(8.dp)
					.fillMaxWidth()
				
			) {
				Text(text = gameMode.modeName)
				
			}
		}

	}
}
