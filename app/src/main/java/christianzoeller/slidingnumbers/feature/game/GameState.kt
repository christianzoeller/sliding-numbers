package christianzoeller.slidingnumbers.feature.game

enum class GameStatus {
    NotStarted,
    Running,
    Finished
}

data class GameState(
    val status: GameStatus = GameStatus.NotStarted,
    val values: List<Int> = List(16) { 0 }
)

fun GameState.swipeRight(): GameState? {
    if (status != GameStatus.Running) return null

    return this
}

fun GameState.swipeLeft(): GameState? {
    if (status != GameStatus.Running) return null

    return this
}

fun GameState.swipeUp(): GameState? {
    if (status != GameStatus.Running) return null

    return this
}

fun GameState.swipeDown(): GameState? {
    if (status != GameStatus.Running) return null

    return this
}