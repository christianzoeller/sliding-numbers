package christianzoeller.slidingnumbers.repository

import christianzoeller.slidingnumbers.ApplicationScope
import christianzoeller.slidingnumbers.datasource.GameResultDao
import christianzoeller.slidingnumbers.model.GameResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameResultRepository @Inject constructor(
    @ApplicationScope private val externalScope: CoroutineScope,
    private val gameResultDao: GameResultDao
) {
    suspend fun addGameResult(result: GameResult) {
        externalScope
            .launch { gameResultDao.insert(result) }
            .join()
    }

    suspend fun getGameResultById(id: Long) =
        gameResultDao.getById(id)

    fun getAllResults() =
        gameResultDao.getAll()
}