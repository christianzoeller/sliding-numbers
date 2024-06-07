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
