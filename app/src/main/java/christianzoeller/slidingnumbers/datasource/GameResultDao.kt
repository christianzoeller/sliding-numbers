package christianzoeller.slidingnumbers.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import christianzoeller.slidingnumbers.model.GameResult

@Dao
interface GameResultDao {
    @Insert
    suspend fun insert(result: GameResult)

    @Query("SELECT * FROM results")
    suspend fun getAll(): List<GameResult>
}