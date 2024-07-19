package christianzoeller.slidingnumbers.repository

import christianzoeller.slidingnumbers.datasource.GameResultDao
import christianzoeller.slidingnumbers.model.GameResult
import javax.inject.Inject

class GameResultRepository @Inject constructor(
    private val gameResultDao: GameResultDao
) {
    suspend fun addGameResult(result: GameResult) =
        gameResultDao.insert(result)

    suspend fun getGameResultById(id: Long) =
        gameResultDao.getById(id)

    suspend fun getAllResults() =
        gameResultDao.getAll()
}