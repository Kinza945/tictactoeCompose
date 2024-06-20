package com.kynzai.tictactoe.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kynzai.tictactoe.data.PointAction
import com.kynzai.tictactoe.data.TurnType
import com.kynzai.tictactoe.data.WinType
import com.kynzai.tictactoe.model.toast
import com.kynzai.tictactoe.ui.component.PlayerItem
import com.kynzai.tictactoe.ui.component.RoundItem
import com.kynzai.tictactoe.ui.component.TicTacToeBoard
import com.kynzai.tictactoe.ui.viewmodel.GameViewModel

@Composable
fun GameScreen(
	navController: NavController,
	viewModel: GameViewModel
) {
	LaunchedEffect(viewModel.winner) {
		when (viewModel.winner) {
			WinType.Tie -> "Tie".toast()
			WinType.O -> {
				if (viewModel.playerOne.pointType == PointAction.O) "${viewModel.playerOne.name} win".toast()
				else "${viewModel.playerTwo.name} win".toast()
			}
			WinType.X -> {
				if (viewModel.playerOne.pointType == PointAction.X) "${viewModel.playerOne.name} win".toast()
				else "${viewModel.playerTwo.name} win".toast()
			}
			WinType.None -> {}
		}
		
		viewModel.clearBoard()
	}

	Column(
		modifier = Modifier
			.systemBarsPadding()
			.fillMaxSize()
			.background(MaterialTheme.colorScheme.surface)
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
			modifier = Modifier
				.padding(16.dp)
				.fillMaxWidth()
		) {
			PlayerItem(
				player = viewModel.playerOne,
				playerTurn = viewModel.currentTurn == TurnType.PlayerOne
			)
			
			RoundItem(
				round = viewModel.round,
				draw = viewModel.draw
			)
			
			PlayerItem(
				player = viewModel.playerTwo,
				playerTurn = viewModel.currentTurn == TurnType.PlayerTwo
			)
		}
		
		Box(
			contentAlignment = Alignment.Center,
			modifier = Modifier
				.weight(1f)
		) {
			TicTacToeBoard(
				board = viewModel.board,
				onClick = { row, col ->
					viewModel.updateBoard(row, col)
				},
				modifier = Modifier
					.fillMaxWidth()
					.aspectRatio(1f / 1f)
			)
		}
	}
}
