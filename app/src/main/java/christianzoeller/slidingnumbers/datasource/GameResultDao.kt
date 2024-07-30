package christianzoeller.slidingnumbers.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import christianzoeller.slidingnumbers.model.GameResult
import kotlinx.coroutines.flow.Flow

@Dao
interface GameResultDao {
    @Insert
    suspend fun insert(result: GameResult)

    @Query("SELECT * FROM results WHERE id = :id")
    suspend fun getById(id: Long): GameResult

    @Query("SELECT * FROM results")
    fun getAll(): Flow<List<GameResult>>
}