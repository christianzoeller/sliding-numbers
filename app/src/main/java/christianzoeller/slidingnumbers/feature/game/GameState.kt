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

/**
 * Updates the board accordingly for a swipe to the right.
 *
 * If this move is not possible, i.e. it has no effect, the same [GameState]
 * is returned. Only returns null in case the game is not in [GameStatus.Running].
 */
fun GameState.swipeRight(): GameState? {
    if (status != GameStatus.Running) return null

    return this
}

/**
 * Updates the board accordingly for a swipe to the left.
 *
 * If this move is not possible, i.e. it has no effect, the same [GameState]
 * is returned. Only returns null in case the game is not in [GameStatus.Running].
 */
fun GameState.swipeLeft(): GameState? {
    if (status != GameStatus.Running) return null

    return this
}

/**
 * Updates the board accordingly for a swipe up.
 *
 * If this move is not possible, i.e. it has no effect, the same [GameState]
 * is returned. Only returns null in case the game is not in [GameStatus.Running].
 */
fun GameState.swipeUp(): GameState? {
    if (status != GameStatus.Running) return null

    return this
}

/**
 * Updates the board accordingly for a swipe down.
 *
 * If this move is not possible, i.e. it has no effect, the same [GameState]
 * is returned. Only returns null in case the game is not in [GameStatus.Running].
 */
fun GameState.swipeDown(): GameState? {
    if (status != GameStatus.Running) return null

    return this
}

/**
 * Adds a new tile with either the number 2 or 4 to the board.
 *
 * In case that is not possible because the board is full, null is returned.
 * Null is also returned if the game is not in [GameStatus.Running].
 */
fun GameState.addTile(): GameState? {
    if (status != GameStatus.Running) return null

    return this
}