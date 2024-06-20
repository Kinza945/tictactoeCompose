package com.kynzai.tictactoe.algorithm

import com.kynzai.tictactoe.data.GameMode
import com.kynzai.tictactoe.data.PointAction
import com.kynzai.tictactoe.data.TurnType
import com.kynzai.tictactoe.data.WinType
import com.kynzai.tictactoe.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
доска
0|1|2
3|4|5
6|7|8
 */
class Algorithm(
	private var playerOne: Player,
	private var playerTwo: Player,
) {

	private val algorithmMinimax = AlgorithmMinimax(this)

	// Инициализация пустой доски
	private val defaultBoard = ArrayList<PointAction>(9).apply {
		repeat(9) { i ->
			add(i, PointAction.Empty)
		}
	}

	// Текущее состояние доски
	private val _board = MutableStateFlow(defaultBoard)
	val board: StateFlow<ArrayList<PointAction>> = _board.asStateFlow()

	// Текущий ход
	private val _currentTurn = MutableStateFlow(TurnType.PlayerOne)
	val currentTurn: StateFlow<TurnType> = _currentTurn.asStateFlow()

	private var gameMode: GameMode = GameMode.Computer
	private var listener: Listener? = null

	// Проверка победы по горизонталям
	private fun checkHorizontal(board: List<PointAction>): WinType {
		for (i in 0 until 3) {
			when {
				board[i * 3] == PointAction.O && board[(i * 3) + 1] == PointAction.O && board[(i * 3) + 2] == PointAction.O -> {
					return WinType.O
				}
				board[i * 3] == PointAction.X && board[(i * 3) + 1] == PointAction.X && board[(i * 3) + 2] == PointAction.X -> {
					return WinType.X
				}
			}
		}
		return WinType.None
	}

	// Проверка победы по вертикалям
	private fun checkVertical(board: List<PointAction>): WinType {
		for (i in 0 until 3) {
			when {
				board[i] == PointAction.O && board[i + 3] == PointAction.O && board[i + 6] == PointAction.O -> {
					return WinType.O
				}
				board[i] == PointAction.X && board[i + 3] == PointAction.X && board[i + 6] == PointAction.X -> {
					return WinType.X
				}
			}
		}
		return WinType.None
	}

	// Проверка победы по диагоналям
	private fun checkDiagonal(board: List<PointAction>): WinType {
		when {
			board[0] == PointAction.O && board[4] == PointAction.O && board[8] == PointAction.O -> {
				return WinType.O
			}
			board[0] == PointAction.X && board[4] == PointAction.X && board[8] == PointAction.X -> {
				return WinType.X
			}
			board[2] == PointAction.O && board[4] == PointAction.O && board[6] == PointAction.O -> {
				return WinType.O
			}
			board[2] == PointAction.X && board[4] == PointAction.X && board[6] == PointAction.X -> {
				return WinType.X
			}
		}
		return WinType.None
	}

	// Проверка победы (включая вызов слушателя, если необходимо)
	fun checkWin(board: List<PointAction>, invokeListener: Boolean = true): WinType {
		val wins = listOf(checkHorizontal(board), checkVertical(board), checkDiagonal(board))
		val isTie = wins.all { it == WinType.None } && board.all { it != PointAction.Empty }

		val winner = when {
			isTie -> WinType.Tie
			WinType.O in wins -> WinType.O
			WinType.X in wins -> WinType.X
			else -> WinType.None
		}

		if (invokeListener) {
			listener?.onWin(winner)
		}

		return winner
	}

	// Обновление доски и проверка следующего хода
	suspend fun updateBoard(index: Int) {
		val newPointType = if (currentTurn.value == TurnType.PlayerOne) playerOne.pointType else playerTwo.pointType
		val newBoard = ArrayList(board.value).apply {
			set(index, newPointType)
		}

		_board.emit(newBoard)

		val winner = checkWin(newBoard)

		val nextTurn = if (currentTurn.value == TurnType.PlayerOne) TurnType.PlayerTwo else TurnType.PlayerOne

		_currentTurn.emit(nextTurn)

		if (winner == WinType.None) {
			when (nextTurn) {
				TurnType.PlayerOne -> {
					if (playerOne.id == Player.Computer.id) {
						computerTurn(newBoard)
					}
				}
				TurnType.PlayerTwo -> {
					if (playerTwo.id == Player.Computer.id) {
						computerTurn(newBoard)
					}
				}
			}
		}
	}

	// Ход компьютера
	private suspend fun computerTurn(board: List<PointAction>) {
		val emptyIndex = arrayListOf<Int>()

		board.forEachIndexed { i, type ->
			if (type == PointAction.Empty) emptyIndex.add(i)
		}

		if (emptyIndex.isNotEmpty()) {
			val nextIndex = algorithmMinimax.getBestMove(board, playerTwo, playerOne)
			updateBoard(nextIndex)
		}
	}

	// Очистка доски и подготовка к новой игре
	suspend fun clearBoard() {
		_board.emit(defaultBoard)

		when (currentTurn.value) {
			TurnType.PlayerOne -> {
				if (playerOne.id == Player.Computer.id) {
					computerTurn(defaultBoard)
				}
			}
			TurnType.PlayerTwo -> {
				if (playerTwo.id == Player.Computer.id) {
					computerTurn(defaultBoard)
				}
			}
		}
	}

	// Обновление информации о игроках
	fun updatePlayer(one: Player, two: Player) {
		playerOne = one
		playerTwo = two
	}

	// Установка слушателя для отслеживания побед
	fun setListener(l: Listener) {
		listener = l
	}

	// Интерфейс слушателя для обработки события победы
	interface Listener {
		fun onWin(type: WinType)
	}
}
