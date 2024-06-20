package com.kynzai.tictactoe.data

import androidx.navigation.NavType
import androidx.navigation.navArgument

//TODO: доделать файл
sealed class TicTacToeScreen(val route: String) {

	class Dashboard {
		object Root: TicTacToeScreen("dashboard/root")
		object Home: TicTacToeScreen("dashboard/home")
	}
	
	class Score {
		object Root: TicTacToeScreen("score/root")
		object Home: TicTacToeScreen("score/home")
	}
	
	class Game {
		object Root: TicTacToeScreen("game/root")
		object Home: TicTacToeScreen(
			route = "game/home?" +
					"$ARG_GAME_MODE={$ARG_GAME_MODE}"
		) {
			fun createRoute(gameMode: GameMode): String {
				return "game/home?" +
						"$ARG_GAME_MODE=${gameMode.ordinal}"
			}
			
			val arguments = listOf(
				navArgument(ARG_GAME_MODE) {
					type = NavType.IntType
				}
			)
		}
	}

}

const val ARG_GAME_MODE = "game_mode"
