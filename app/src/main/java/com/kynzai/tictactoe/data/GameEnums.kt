package com.kynzai.tictactoe.data

enum class GameMode {
    Computer,
    PvP,
    PvPBluetooth
}

enum class PointAction {
    Empty,
    X,
    O
}
enum class TurnType {
    PlayerOne,
    PlayerTwo
}
enum class WinType {
    None,
    Tie,
    O,
    X
}