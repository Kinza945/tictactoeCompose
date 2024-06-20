package com.kynzai.tictactoe.model

import com.kynzai.tictactoe.data.PointAction

data class Player(
	val id: Int,
	val win: Int,
	val name: String,
	var pointType: PointAction
) {
	companion object {
		val Player1 = Player(
			id = 0,
			win = 0,
			name = "Player 1",
			pointType = PointAction.X
		)
		
		val Player2 = Player(
			id = 1,
			win = 0,
			name = "Player 2",
			pointType = PointAction.O
		)
		
		val Computer = Player(
			id = 2,
			win = 0,
			name = "Computer",
			pointType = PointAction.O
		)
	}
}
