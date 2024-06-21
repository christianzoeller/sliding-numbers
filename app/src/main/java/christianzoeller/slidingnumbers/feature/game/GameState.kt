package christianzoeller.slidingnumbers.feature.game

import kotlin.math.abs

private const val boardLength = 4
private const val numberOfTiles = boardLength * boardLength
private const val targetValue = 2048

enum class GameStatus {
    NotStarted,
    Running,
    Finished
}

data class GameState(
    val status: GameStatus = GameStatus.NotStarted,
    val values: List<Int> = List(numberOfTiles) { 0 },
    val score: Int = 0
) {
    val highest: Int
        get() = values.max()

    val won: Boolean
        get() = highest >= targetValue
}

/**
 * Updates the board accordingly for a swipe to the right.
 *
 * If this move is not possible, i.e. it has no effect, the same [GameState]
 * is returned. Only returns null in case the game is not in [GameStatus.Running].
 */
fun GameState.swipeRight(): GameState? {
    if (status != GameStatus.Running) return null

    return move(numberOfTiles - 1, 0, 1)
}

/**
 * Updates the board accordingly for a swipe to the left.
 *
 * If this move is not possible, i.e. it has no effect, the same [GameState]
 * is returned. Only returns null in case the game is not in [GameStatus.Running].
 */
fun GameState.swipeLeft(): GameState? {
    if (status != GameStatus.Running) return null

    return move(0, 0, -1)
}

/**
 * Updates the board accordingly for a swipe up.
 *
 * If this move is not possible, i.e. it has no effect, the same [GameState]
 * is returned. Only returns null in case the game is not in [GameStatus.Running].
 */
fun GameState.swipeUp(): GameState? {
    if (status != GameStatus.Running) return null

    return move(0, -1, 0)
}

/**
 * Updates the board accordingly for a swipe down.
 *
 * If this move is not possible, i.e. it has no effect, the same [GameState]
 * is returned. Only returns null in case the game is not in [GameStatus.Running].
 */
fun GameState.swipeDown(): GameState? {
    if (status != GameStatus.Running) return null

    return move(numberOfTiles - 1, 1, 0)
}

/**
 * Adds a new tile with either the number 2 or 4 to the board.
 *
 * In case that is not possible because the board is full, null is returned.
 * Null is also returned if the game is not in [GameStatus.Running].
 */
fun GameState.addTile(): GameState? {
    if (status != GameStatus.Running) return null

    val freeTiles = buildList {
        values.forEachIndexed { index, value ->
            value
                .takeIf { it == 0 }
                ?.let { add(index) }
        }
    }

    if (freeTiles.isEmpty()) return null

    val randomNumber = (1..10).random()
    val newNumber = if (randomNumber == 10) 4 else 2
    val insertionIndex = freeTiles.random()

    val updatedValues = values.toMutableList()
    updatedValues[insertionIndex] = newNumber

    return this.copy(values = updatedValues)
}

/**
 * Performs the given move and updates the game state accordingly.
 *
 * If this move is not possible, i.e. it has no effect, the same [GameState]
 * is returned. Only returns null in case the game is not in [GameStatus.Running].
 */
private fun GameState.move(
    countDownFrom: Int,
    yDelta: Int,
    xDelta: Int
): GameState? {
    if (status != GameStatus.Running) return null

    val board = values
        .map { if (it == 0) null else TileValue(it) }
        .chunked(boardLength)
        .map { it.toMutableList() }
        .toMutableList()

    var newScore: Int? = null
    for (i in 0 until numberOfTiles) {
        val j = abs(countDownFrom - i)

        var row = j / boardLength
        var column = j % boardLength

        board[row][column]?.let { current ->
            var nextRow = row + yDelta
            var nextColumn = column + xDelta

            while (nextRow in 0..<boardLength && nextColumn in 0..<boardLength) {
                val next = board[nextRow][nextColumn]
                if (next == null) {
                    board[nextRow][nextColumn] = current
                    board[row][column] = null
                    row = nextRow
                    column = nextColumn
                    nextRow += yDelta
                    nextColumn += xDelta
                }
                else {
                    next.mergeWith(current)?.let {
                        board[nextRow][nextColumn] = it
                        newScore = score + it.value
                        board[row][column] = null
                    }

                    break
                }
            }
        }
    }

    val updatedValues = board
        .flatten()
        .map { it?.value ?: 0 }

    return copy(
        values = updatedValues,
        score = newScore ?: score
    )
}

/**
 * A convenience class that holds the current value of a tile as well as further
 * information on it in the context of an ongoing move.
 */
private data class TileValue(
    val value: Int,
    val merged: Boolean = false
) {
    /**
     * Attempts to merge this [TileValue] with some [other] value.
     * Returns the merged tile or null if they cannot be merged.
     */
    fun mergeWith(other: TileValue?) = if (canBeMergedWith(other)) {
        copy(
            value = value + value,
            merged = true
        )
    } else {
        null
    }

    /**
     * Checks whether this [TileValue] can be merged with some [other] value.
     */
    private fun canBeMergedWith(other: TileValue?) = other?.let {
        !merged && !other.merged && value == other.value
    } ?: false
}