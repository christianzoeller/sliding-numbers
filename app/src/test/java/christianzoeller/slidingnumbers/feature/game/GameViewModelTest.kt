package christianzoeller.slidingnumbers.feature.game

import christianzoeller.slidingnumbers.datasource.GameResultDao
import christianzoeller.slidingnumbers.feature.game.model.SwipeDirection
import christianzoeller.slidingnumbers.model.GameResult
import christianzoeller.slidingnumbers.repository.GameResultRepository
import kotlinx.datetime.Clock
import org.junit.Assert
import org.junit.Test

class GameViewModelTest {
    @Test
    fun `initialized view model has correct state`() {
        val viewModel = GameViewModel(GameResultRepository(FakeGameResultDao))

        val state = viewModel.state.value
        Assert.assertEquals(GameStatus.NotStarted, state.status)
        Assert.assertEquals(0, state.score)
        Assert.assertEquals(0, state.highest)
        Assert.assertFalse(state.won)

        Assert.assertNotEquals(0, state.values.size)
        state.values.forEach {
            Assert.assertEquals(0, it)
        }
    }

    @Test
    fun `starting the game works as expected`() {
        val viewModel = GameViewModel(GameResultRepository(FakeGameResultDao))

        viewModel.onStart()

        val state = viewModel.state.value
        Assert.assertEquals(GameStatus.Running, state.status)
        Assert.assertEquals(0, state.score)
        Assert.assertTrue(state.highest > 0)
        Assert.assertFalse(state.won)

        val tilesWithValueDifferentFromZero = state.values.count { it > 0 }
        Assert.assertEquals(2, tilesWithValueDifferentFromZero)
    }

    @Test
    fun `swiping has no effect if the game has not been started`() {
        val viewModel = GameViewModel(GameResultRepository(FakeGameResultDao))

        SwipeDirection.entries.forEach { direction ->
            viewModel.onSwipe(direction)

            val state = viewModel.state.value
            Assert.assertEquals(GameStatus.NotStarted, state.status)
            Assert.assertEquals(0, state.score)
            Assert.assertEquals(0, state.highest)
            Assert.assertFalse(state.won)

            Assert.assertNotEquals(0, state.values.size)
            state.values.forEach {
                Assert.assertEquals(0, it)
            }
        }
    }

    @Test
    fun `restart has no effect if the game has not been started`() {
        val viewModel = GameViewModel(GameResultRepository(FakeGameResultDao))

        viewModel.onRestart()

        val state = viewModel.state.value
        Assert.assertEquals(GameStatus.NotStarted, state.status)
        Assert.assertEquals(0, state.score)
        Assert.assertEquals(0, state.highest)
        Assert.assertFalse(state.won)

        Assert.assertNotEquals(0, state.values.size)
        state.values.forEach {
            Assert.assertEquals(0, it)
        }
    }
}

// region Mocks
private object FakeGameResultDao : GameResultDao {
    override suspend fun insert(result: GameResult) {}
    override suspend fun getById(id: Long) = GameResult(
        score = 0,
        highest = 0,
        timestamp = Clock.System.now(),
        finalValues = List(16) { 0 }
    )

    override suspend fun getAll(): List<GameResult> = emptyList()

}
// endregion