package christianzoeller.slidingnumbers

import androidx.room.Database
import androidx.room.RoomDatabase
import christianzoeller.slidingnumbers.datasource.GameResultDao
import christianzoeller.slidingnumbers.model.GameResult

@Database(entities = [GameResult::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameResultDao(): GameResultDao
}
