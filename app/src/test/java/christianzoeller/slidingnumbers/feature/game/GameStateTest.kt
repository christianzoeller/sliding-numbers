package christianzoeller.slidingnumbers.feature.game

import org.junit.Assert
import org.junit.Test

class GameStateTest {
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
            0, 0, 2, 0,
            0, 2, 4, 8,
            0, 0, 0, 2,
            8, 4, 2, 4
        )

        val result = originalState.swipeRight()

        Assert.assertNotNull(result)
        Assert.assertEquals(expected, result!!.values)
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
    }
    // endregion
}

private fun makeGameState(values: List<Int>) = GameState(
    status = GameStatus.Running,
    values = values
)