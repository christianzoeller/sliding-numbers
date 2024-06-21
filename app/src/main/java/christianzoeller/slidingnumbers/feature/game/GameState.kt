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
 * Updates the [GameStatus] of this [GameState] with regard to the current
 * values on the board.
 *
 * If the game is not in [GameStatus.Running], the same [GameState] will be
 * returned.
 */
fun GameState.updateStatus() =
    when (status) {
        GameStatus.NotStarted, GameStatus.Finished -> this
        GameStatus.Running -> {
            if (won || movesLeft() == false) {
                copy(status = GameStatus.Finished)
            } else {
                this
            }
        }
    }

/**
 * Checks whether there are still moves left that the player can do.
 *
 * Returns null if the game is not in [GameStatus.Running].
 */
private fun GameState.movesLeft(): Boolean? {
    if (status != GameStatus.Running) return null

    return swipeRightPossible() ||
            swipeLeftPossible() ||
            swipeUpPossible() ||
            swipeDownPossible()
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

    var newScore = score
    for (i in 0 until numberOfTiles) {
        val j = abs(countDownFrom - i)

        var row = j / boardLength
        var column = j % boardLength

        board[row][column]?.let { current ->
            var nextRow = row + yDelta
            var nextColumn = column + xDelta

            while (nextRow in 0 until boardLength && nextColumn in 0 until boardLength) {
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
                        newScore += it.value
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
        score = newScore
    )
}

/**
 * Checks whether the player can swipe right.
 */
private fun GameState.swipeRightPossible(): Boolean =
    movePossible(numberOfTiles - 1, 0, 1)

/**
 * Checks whether the player can swipe left.
 */
private fun GameState.swipeLeftPossible(): Boolean =
    movePossible(0, 0, -1)

/**
 * Checks whether the player can swipe up.
 */
private fun GameState.swipeUpPossible(): Boolean =
    movePossible(0, -1, 0)

/**
 * Checks whether the player can swipe down.
 */
private fun GameState.swipeDownPossible(): Boolean =
    movePossible(numberOfTiles - 1, 1, 0)

/**
 * Checks whether the given move is possible without actually performing it.
 */
private fun GameState.movePossible(
    countDownFrom: Int,
    yDelta: Int,
    xDelta: Int
): Boolean {
    if (status != GameStatus.Running) return false

    val board = values
        .map { if (it == 0) null else TileValue(it) }
        .chunked(boardLength)

    for (i in 0 until numberOfTiles) {
        val j = abs(countDownFrom - i)

        val row = j / boardLength
        val column = j % boardLength

        val current = board[row][column]
        if (current == null) {
            return true
        } else {
            val nextRow = row + yDelta
            val nextColumn = column + xDelta
            if (nextRow in 0 until boardLength && nextColumn in 0 until boardLength) {
                val next = board[nextRow][nextColumn]
                if (next == null || next.canBeMergedWith(current)) {
                    return true
                }
            }
        }
    }

    return false
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
    fun canBeMergedWith(other: TileValue?) = other?.let {
        !merged && !other.merged && value == other.value
    } ?: false
}