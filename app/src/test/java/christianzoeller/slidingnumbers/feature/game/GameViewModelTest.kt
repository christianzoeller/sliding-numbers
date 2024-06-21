package christianzoeller.slidingnumbers.feature.game

import christianzoeller.slidingnumbers.feature.game.model.SwipeDirection
import org.junit.Assert
import org.junit.Test

class GameViewModelTest {
    @Test
    fun `initialized view model has correct state`() {
        val viewModel = GameViewModel()

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
        val viewModel = GameViewModel()

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
        val viewModel = GameViewModel()

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
        val viewModel = GameViewModel()

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