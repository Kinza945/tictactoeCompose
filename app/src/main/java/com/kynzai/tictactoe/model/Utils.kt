package com.kynzai.tictactoe.model

import android.content.Context
import com.kynzai.tictactoe.data.GameMode
import com.kynzai.tictactoe.data.PointAction
import com.kynzai.tictactoe.data.WinType
import com.kynzai.tictactoe.runtime.TicTacToeApplication

lateinit var app: TicTacToeApplication

lateinit var appContext: Context

fun PointAction.toWinType(): WinType {
    return when (this) {
        PointAction.Empty -> WinType.None
        PointAction.X -> WinType.X
        PointAction.O -> WinType.O
    }
}

val GameMode.modeName: String
    get() = when(this) {
        GameMode.Computer -> "Player vs AI"
        GameMode.PvP -> "Player vs Player"
        GameMode.PvPBluetooth -> "PvP Bluetooth"
    }
