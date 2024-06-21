package christianzoeller.slidingnumbers.feature.game

import org.junit.Assert
import org.junit.Test

class GameStateTest {
    // region General
    @Test
    fun `swiping has no effect if game is not running`() {
        val notStarted = GameState(status = GameStatus.NotStarted, values = List(16) { 0 })
        val finished = GameState(status = GameStatus.Finished, values = List(16) { 0 })

        Assert.assertNull(notStarted.swipeRight())
        Assert.assertNull(finished.swipeRight())

        Assert.assertNull(notStarted.swipeLeft())
        Assert.assertNull(finished.swipeLeft())

        Assert.assertNull(notStarted.swipeUp())
        Assert.assertNull(finished.swipeUp())

        Assert.assertNull(notStarted.swipeDown())
        Assert.assertNull(finished.swipeDown())
    }

    @Test
    fun `adding a tile has no effect if the game is not running`() {
        val notStarted = GameState(status = GameStatus.NotStarted, values = List(16) { 0 })
        val finished = GameState(status = GameStatus.Finished, values = List(16) { 0 })

        Assert.assertNull(notStarted.addTile())
        Assert.assertNull(finished.addTile())
    }

    @Test
    fun `adding a tile has no effect if the board is full`() {
        val full = List(16) { 2 }
        val fullState = makeGameState(full)

        Assert.assertNull(fullState.addTile())
    }

    @Test
    fun `adding a tile works if there is available space in a running game`() {
        // This test is obviously far from perfect but good enough for what's
        // at stake here.
        val originalValues = listOf(
            0, 4, 8, 2,
            2, 2, 0, 0,
            8, 0, 8, 4,
            0, 0, 2, 4
        )
        val originalState = makeGameState(originalValues)
        val originalSum = originalValues.sum()

        for (i in 1..10) {
            val result = originalState.addTile()

            Assert.assertNotNull(result)
            Assert.assertEquals(4, result!!.values[1])
            Assert.assertEquals(8, result.values[2])
            Assert.assertEquals(2, result.values[3])
            Assert.assertEquals(2, result.values[4])
            Assert.assertEquals(2, result.values[5])
            Assert.assertEquals(8, result.values[8])
            Assert.assertEquals(8, result.values[10])
            Assert.assertEquals(4, result.values[11])
            Assert.assertEquals(2, result.values[14])
            Assert.assertEquals(4, result.values[15])

            val newSum = result.values.sum()
            val sumIsOk = newSum == originalSum + 2 || newSum == originalSum + 4

            Assert.assertEquals(true, sumIsOk)
        }
    }
    // endregion

    // region Swiping right
    @Test
    fun `swipe right works with no movement`() {
        val originalValues = listOf(
            0, 0, 0, 2,
            0, 2, 4, 8,
            0, 0, 0, 0,
            8, 4, 2, 4
        )
        val originalState = makeGameState(originalValues)

        val result = originalState.swipeRight()

        Assert.assertNotNull(result)
        Assert.assertEquals(originalValues, result!!.values)
        Assert.assertEquals(8, result.highest)
        Assert.assertEquals(0, result.score)
    }

    @Test
    fun `swipe right works with movement but no additions`() {
        val originalValues = listOf(
            0, 0, 2, 0,
            0, 2, 4, 8,
            2, 0, 0, 0,
            8, 4, 2, 4
        )
        val originalState = makeGameState(originalValues)

        val expected = listOf(
            0, 0, 0, 2,
            0, 2, 4, 8,
            0, 0, 0, 2,
            8, 4, 2, 4
        )

        val result = originalState.swipeRight()

        Assert.assertNotNull(result)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(8, result.highest)
        Assert.assertEquals(0, result.score)
    }

    @Test
    fun `swipe right works with one addition`() {
        val originalValues = listOf(
            0, 0, 2, 2,
            0, 2, 4, 8,
            2, 0, 0, 0,
            8, 4, 2, 4
        )
        val originalState = makeGameState(originalValues)

        val expected = listOf(
            0, 0, 0, 4,
            0, 2, 4, 8,
            0, 0, 0, 2,
            8, 4, 2, 4
        )

        val result = originalState.swipeRight()

        Assert.assertNotNull(originalState)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(8, result.highest)
        Assert.assertEquals(4, result.score)
    }

    @Test
    fun `swipe right works with multiple additions`() {
        val originalValues = listOf(
            0, 0, 2, 2,
            0, 2, 4, 8,
            2, 2, 0, 0,
            8, 4, 4, 4
        )
        val originalState = makeGameState(originalValues)

        val expected = listOf(
            0, 0, 0, 4,
            0, 2, 4, 8,
            0, 0, 0, 4,
            0, 8, 4, 8
        )

        val result = originalState.swipeRight()

        Assert.assertNotNull(originalState)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(8, result.highest)
        Assert.assertEquals(4 + 0 + 4 + 8, result.score)
    }

    @Test
    fun `swipe right is not greedy`() {
        val originalValues = listOf(
            0, 0, 0, 0,
            2, 2, 2, 2,
            0, 0, 0, 0,
            0, 0, 0, 0
        )
        val originalState = makeGameState(originalValues)

        val expected = listOf(
            0, 0, 0, 0,
            0, 0, 4, 4,
            0, 0, 0, 0,
            0, 0, 0, 0
        )

        val result = originalState.swipeRight()

        Assert.assertNotNull(originalState)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(4, result.highest)
        Assert.assertEquals(4 + 4, result.score)
    }

    @Test
    fun `swipe right respects priority`() {
        val originalValues = listOf(
            0, 0, 0, 0,
            0, 2, 2, 2,
            0, 0, 0, 0,
            0, 0, 0, 0
        )
        val originalState = makeGameState(originalValues)

        val expected = listOf(
            0, 0, 0, 0,
            0, 0, 2, 4,
            0, 0, 0, 0,
            0, 0, 0, 0
        )

        val result = originalState.swipeRight()

        Assert.assertNotNull(originalState)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(4, result.highest)
        Assert.assertEquals(4, result.score)
    }
    // endregion

    // region Swiping left
    @Test
    fun `swipe left works with no movement`() {
        val originalValues = listOf(
            2, 0, 0, 0,
            8, 4, 2, 0,
            0, 0, 0, 0,
            4, 2, 4, 8
        )
        val originalState = makeGameState(originalValues)

        val result = originalState.swipeLeft()

        Assert.assertNotNull(result)
        Assert.assertEquals(originalValues, result!!.values)
        Assert.assertEquals(8, result.highest)
        Assert.assertEquals(0, result.score)
    }

    @Test
    fun `swipe left works with movement but no additions`() {
        val originalValues = listOf(
            2, 0, 0, 0,
            8, 4, 2, 0,
            0, 0, 0, 2,
            4, 2, 4, 8
        )
        val originalState = makeGameState(originalValues)

        val expected = listOf(
            2, 0, 0, 0,
            8, 4, 2, 0,
            2, 0, 0, 0,
            4, 2, 4, 8
        )

        val result = originalState.swipeLeft()

        Assert.assertNotNull(result)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(8, result.highest)
        Assert.assertEquals(0, result.score)
    }

    @Test
    fun `swipe left works with one addition`() {
        val originalValues = listOf(
            2, 2, 0, 0,
            8, 4, 2, 0,
            0, 0, 0, 2,
            4, 2, 4, 8
        )
        val originalState = makeGameState(originalValues)

        val expected = listOf(
            4, 0, 0, 0,
            8, 4, 2, 0,
            2, 0, 0, 0,
            4, 2, 4, 8
        )

        val result = originalState.swipeLeft()

        Assert.assertNotNull(originalState)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(8, result.highest)
        Assert.assertEquals(4, result.score)
    }

    @Test
    fun `swipe left works with multiple additions`() {
        val originalValues = listOf(
            2, 2, 0, 0,
            8, 4, 2, 0,
            0, 0, 2, 2,
            4, 4, 4, 8
        )
        val originalState = makeGameState(originalValues)

        val expected = listOf(
            4, 0, 0, 0,
            8, 4, 2, 0,
            4, 0, 0, 0,
            8, 4, 8, 0
        )

        val result = originalState.swipeLeft()

        Assert.assertNotNull(originalState)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(8, result.highest)
        Assert.assertEquals(4 + 0 + 4 + 8, result.score)
    }

    @Test
    fun `swipe left is not greedy`() {
        val originalValues = listOf(
            0, 0, 0, 0,
            2, 2, 2, 2,
            0, 0, 0, 0,
            0, 0, 0, 0
        )
        val originalState = makeGameState(originalValues)

        val expected = listOf(
            0, 0, 0, 0,
            4, 4, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
        )

        val result = originalState.swipeLeft()

        Assert.assertNotNull(originalState)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(4, result.highest)
        Assert.assertEquals(4 + 4, result.score)
    }

    @Test
    fun `swipe left respects priority`() {
        val originalValues = listOf(
            0, 0, 0, 0,
            2, 2, 2, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
        )
        val originalState = makeGameState(originalValues)

        val expected = listOf(
            0, 0, 0, 0,
            4, 2, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
        )

        val result = originalState.swipeLeft()

        Assert.assertNotNull(originalState)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(4, result.highest)
        Assert.assertEquals(4, result.score)
    }
    // endregion

    // region Swiping up
    @Test
    fun `swipe up works with no movement`() {
        val originalValues = listOf(
            2, 8, 0, 4,
            0, 4, 0, 2,
            0, 2, 0, 4,
            0, 0, 0, 8
        )

        val originalState = makeGameState(originalValues)

        val result = originalState.swipeUp()

        Assert.assertNotNull(result)
        Assert.assertEquals(originalValues, result!!.values)
        Assert.assertEquals(8, result.highest)
        Assert.assertEquals(0, result.score)
    }

    @Test
    fun `swipe up works with movement but no additions`() {
        val originalValues = listOf(
            2, 8, 0, 4,
            0, 4, 0, 2,
            0, 2, 0, 4,
            0, 0, 2, 8
        )

        val originalState = makeGameState(originalValues)

        val expected = listOf(
            2, 8, 2, 4,
            0, 4, 0, 2,
            0, 2, 0, 4,
            0, 0, 0, 8
        )

        val result = originalState.swipeUp()

        Assert.assertNotNull(result)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(8, result.highest)
        Assert.assertEquals(0, result.score)
    }

    @Test
    fun `swipe up works with one addition`() {
        val originalValues = listOf(
            2, 8, 0, 4,
            2, 4, 0, 2,
            0, 2, 0, 4,
            0, 0, 2, 8
        )

        val originalState = makeGameState(originalValues)

        val expected = listOf(
            4, 8, 2, 4,
            0, 4, 0, 2,
            0, 2, 0, 4,
            0, 0, 0, 8
        )

        val result = originalState.swipeUp()

        Assert.assertNotNull(originalState)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(8, result.highest)
        Assert.assertEquals(4, result.score)
    }

    @Test
    fun `swipe up works with multiple additions`() {
        val originalValues = listOf(
            2, 8, 0, 4,
            2, 4, 0, 4,
            0, 2, 2, 4,
            0, 0, 2, 8
        )
        val originalState = makeGameState(originalValues)

        val expected = listOf(
            4, 8, 4, 8,
            0, 4, 0, 4,
            0, 2, 0, 8,
            0, 0, 0, 0
        )

        val result = originalState.swipeUp()

        Assert.assertNotNull(originalState)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(8, result.highest)
        Assert.assertEquals(4 + 0 + 4 + 8, result.score)
    }

    @Test
    fun `swipe up is not greedy`() {
        val originalValues = listOf(
            0, 2, 0, 0,
            0, 2, 0, 0,
            0, 2, 0, 0,
            0, 2, 0, 0
        )
        val originalState = makeGameState(originalValues)

        val expected = listOf(
            0, 4, 0, 0,
            0, 4, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
        )

        val result = originalState.swipeUp()

        Assert.assertNotNull(originalState)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(4, result.highest)
        Assert.assertEquals(4 + 4, result.score)
    }

    @Test
    fun `swipe up respects priority`() {
        val originalValues = listOf(
            0, 2, 0, 0,
            0, 2, 0, 0,
            0, 2, 0, 0,
            0, 0, 0, 0
        )
        val originalState = makeGameState(originalValues)

        val expected = listOf(
            0, 4, 0, 0,
            0, 2, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0
        )

        val result = originalState.swipeUp()

        Assert.assertNotNull(originalState)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(4, result.highest)
        Assert.assertEquals(4, result.score)
    }
    // endregion

    // region Swiping down
    @Test
    fun `swipe down works with no movement`() {
        val originalValues = listOf(
            0, 0, 0, 8,
            0, 2, 0, 4,
            0, 4, 0, 2,
            2, 8, 0, 4
        )

        val originalState = makeGameState(originalValues)

        val result = originalState.swipeDown()

        Assert.assertNotNull(result)
        Assert.assertEquals(originalValues, result!!.values)
        Assert.assertEquals(8, result.highest)
        Assert.assertEquals(0, result.score)
    }

    @Test
    fun `swipe down works with movement but no additions`() {
        val originalValues = listOf(
            0, 0, 2, 8,
            0, 2, 0, 4,
            0, 4, 0, 2,
            2, 8, 0, 4
        )

        val originalState = makeGameState(originalValues)

        val expected = listOf(
            0, 0, 0, 8,
            0, 2, 0, 4,
            0, 4, 0, 2,
            2, 8, 2, 4
        )

        val result = originalState.swipeDown()

        Assert.assertNotNull(result)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(8, result.highest)
        Assert.assertEquals(0, result.score)
    }

    @Test
    fun `swipe down works with one addition`() {
        val originalValues = listOf(
            0, 0, 2, 8,
            0, 2, 0, 4,
            2, 4, 0, 2,
            2, 8, 0, 4
        )

        val originalState = makeGameState(originalValues)

        val expected = listOf(
            0, 0, 0, 8,
            0, 2, 0, 4,
            0, 4, 0, 2,
            4, 8, 2, 4
        )

        val result = originalState.swipeDown()

        Assert.assertNotNull(originalState)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(8, result.highest)
        Assert.assertEquals(4, result.score)
    }

    @Test
    fun `swipe down works with multiple additions`() {
        val originalValues = listOf(
            0, 0, 2, 8,
            0, 2, 2, 4,
            2, 4, 0, 4,
            2, 8, 0, 4
        )
        val originalState = makeGameState(originalValues)

        val expected = listOf(
            0, 0, 0, 0,
            0, 2, 0, 8,
            0, 4, 0, 4,
            4, 8, 4, 8
        )

        val result = originalState.swipeDown()

        Assert.assertNotNull(originalState)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(8, result.highest)
        Assert.assertEquals(4 + 0 + 4 + 8, result.score)
    }

    @Test
    fun `swipe down is not greedy`() {
        val originalValues = listOf(
            0, 2, 0, 0,
            0, 2, 0, 0,
            0, 2, 0, 0,
            0, 2, 0, 0
        )
        val originalState = makeGameState(originalValues)

        val expected = listOf(
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 4, 0, 0,
            0, 4, 0, 0
        )

        val result = originalState.swipeDown()

        Assert.assertNotNull(originalState)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(4, result.highest)
        Assert.assertEquals(4 + 4, result.score)
    }

    @Test
    fun `swipe down respects priority`() {
        val originalValues = listOf(
            0, 0, 0, 0,
            0, 2, 0, 0,
            0, 2, 0, 0,
            0, 2, 0, 0
        )
        val originalState = makeGameState(originalValues)

        val expected = listOf(
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 2, 0, 0,
            0, 4, 0, 0
        )

        val result = originalState.swipeDown()

        Assert.assertNotNull(originalState)
        Assert.assertEquals(expected, result!!.values)
        Assert.assertEquals(4, result.highest)
        Assert.assertEquals(4, result.score)
    }
    // endregion

    // region Update status
    @Test
    fun `update game status works correctly if there are moves left`() {
        val originalValues = listOf(
            0, 0, 0, 2,
            0, 2, 4, 8,
            0, 0, 0, 0,
            8, 4, 2, 4
        )
        val originalState = makeGameState(originalValues)

        val result = originalState.updateStatus()

        Assert.assertEquals(GameStatus.Running, result.status)
    }

    @Test
    fun `update game status works correctly if there are no more moves left`() {
        val originalValues = listOf(
            8, 4, 8, 4,
            4, 2, 4, 2,
            2, 8, 2, 8,
            8, 4, 8, 4
        )
        val originalState = makeGameState(originalValues)

        val result = originalState.updateStatus()

        Assert.assertEquals(GameStatus.Finished, result.status)
        Assert.assertFalse(result.won)
    }

    @Test
    fun `update game status works correctly if the player won`() {
        val originalValues = listOf(
            0, 0, 0, 2,
            0, 2, 4, 8,
            0, 0, 0, 0,
            8, 4, 2, 2048
        )
        val originalState = makeGameState(originalValues)

        val result = originalState.updateStatus()

        Assert.assertEquals(GameStatus.Finished, result.status)
        Assert.assertTrue(result.won)
    }

    @Test
    fun `update game status works correctly if the player won with no moves left`() {
        val originalValues = listOf(
            8, 4, 8, 4,
            4, 2048, 4, 2,
            2, 8, 2, 8,
            8, 4, 8, 4
        )
        val originalState = makeGameState(originalValues)

        val result = originalState.updateStatus()

        Assert.assertEquals(GameStatus.Finished, result.status)
        Assert.assertTrue(result.won)
    }

    // endregion
}

private fun makeGameState(values: List<Int>) = GameState(
    status = GameStatus.Running,
    values = values
)