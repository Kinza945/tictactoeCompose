package com.kynzai.tictactoe.algorithm

import com.kynzai.tictactoe.data.PointAction
import com.kynzai.tictactoe.model.toWinType
import com.kynzai.tictactoe.model.Player
import kotlin.math.abs


class AlgorithmMinimax(private val algorithm: Algorithm) {

	private var maxDepth = 24

	private fun minimaxAlgorithm(
        board: MutableList<PointAction>,
        computerPlayer: Player,
        humanPlayer: Player,
        depth: Int,
        isMax: Boolean,
        alpha: Int,
        beta: Int
	): Int {

		val winner = algorithm.checkWin(board, false)

		val score = if (humanPlayer.pointType.toWinType() == winner) {
			-1
		} else if (computerPlayer.pointType.toWinType() == winner) {
			1
		} else 0

		if (abs(score) == 1 || depth == 0 || board.none { it == PointAction.Empty }) {
			return score
		}

		var alphaVar = alpha
		var betaVar = beta

		return if (isMax) {
			var highestVal = Int.MIN_VALUE
			board.forEachIndexed { index, pointType ->
				if (pointType == PointAction.Empty) {
					board[index] = computerPlayer.pointType
					highestVal = highestVal.coerceAtLeast(
						minimaxAlgorithm(board, computerPlayer, humanPlayer, depth - 1, false, alphaVar, betaVar)
					)
					board[index] = PointAction.Empty // восстановление состояния доски
					alphaVar = alphaVar.coerceAtLeast(highestVal)
					if (betaVar <= alphaVar) return highestVal // альфа-бета отсечение
				}
			}
			highestVal
		} else {
			var lowestVal = Int.MAX_VALUE
			board.forEachIndexed { index, pointType ->
				if (pointType == PointAction.Empty) {
					board[index] = humanPlayer.pointType
					lowestVal = lowestVal.coerceAtMost(
						minimaxAlgorithm(board, computerPlayer, humanPlayer, depth - 1, true, alphaVar, betaVar)
					)
					board[index] = PointAction.Empty // восстановление состояния доски
					betaVar = betaVar.coerceAtMost(lowestVal)
					if (betaVar <= alphaVar) return lowestVal // альфа-бета отсечение
				}
			}
			lowestVal
		}
	}

	fun getBestMove(
        board: List<PointAction>,
        computerPlayer: Player,
        humanPlayer: Player,
	): Int {
		var index = -1
		var bestValue = Int.MIN_VALUE

		val mutableBoard = board.toMutableList()

		board.forEachIndexed { i, type ->
			if (type == PointAction.Empty) {
				mutableBoard[i] = computerPlayer.pointType

				val moveValue = minimaxAlgorithm(mutableBoard, computerPlayer, humanPlayer, maxDepth, false, Int.MIN_VALUE, Int.MAX_VALUE)

				mutableBoard[i] = PointAction.Empty // восстановление состояния доски

				if (moveValue > bestValue) {
					index = i
					bestValue = moveValue
				}
			}
		}

		return index
	}

}