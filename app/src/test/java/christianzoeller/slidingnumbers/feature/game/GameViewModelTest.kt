package christianzoeller.slidingnumbers.feature.game

import christianzoeller.slidingnumbers.datasource.GameResultDao
import christianzoeller.slidingnumbers.feature.game.model.SwipeDirection
import christianzoeller.slidingnumbers.model.GameResult
import christianzoeller.slidingnumbers.repository.GameResultRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.junit.Assert
import org.junit.Test

class GameViewModelTest {
    @Test
    fun `initialized view model has correct state`() = runTest {
        val viewModel = makeGameViewModel()

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
    fun `starting the game works as expected`() = runTest {
        val viewModel = makeGameViewModel()

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
    fun `swiping has no effect if the game has not been started`() = runTest {
        val viewModel = makeGameViewModel()

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
    fun `restart has no effect if the game has not been started`() = runTest {
        val viewModel = makeGameViewModel()

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
private fun TestScope.makeGameViewModel() = GameViewModel(
    gameResultRepository = GameResultRepository(
        externalScope = this,
        gameResultDao = FakeGameResultDao
    )
)

private object FakeGameResultDao : GameResultDao {
    override suspend fun insert(result: GameResult) {}
    override suspend fun getById(id: Long) = GameResult(
        score = 0,
        highest = 0,
        timestamp = Clock.System.now(),
        finalValues = List(16) { 0 }
    )

    override fun getAll(): Flow<List<GameResult>> = flow {
        emptyList<GameResult>()
    }
}
// endregion