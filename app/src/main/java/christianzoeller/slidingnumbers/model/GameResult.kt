package christianzoeller.slidingnumbers.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.datetime.Instant

@Entity(tableName = "results")
@TypeConverters(InstantTimeConverters::class, ListOfIntConverters::class)
data class GameResult(
    val score: Int,
    val highest: Int,
    val timestamp: Instant,

    @ColumnInfo("final_values")
    val finalValues: List<Int>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

class InstantTimeConverters {
    @TypeConverter
    fun fromLong(value: Long?): Instant? {
        return value?.let { Instant.fromEpochMilliseconds(it) }
    }

    @TypeConverter
    fun toLong(instant: Instant?): Long? {
        return instant?.toEpochMilliseconds()
    }
}

class ListOfIntConverters {
    @TypeConverter
    fun fromString(value: String?): List<Int>? {
        return value
            ?.split(",")
            ?.mapNotNull { it.toIntOrNull() }
            ?.takeIf { it.size == 16 }
    }

    @TypeConverter
    fun toString(values: List<Int>?): String? {
        return values?.joinToString(",")
    }
}
