package christianzoeller.slidingnumbers

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            context = appContext,
            klass = AppDatabase::class.java,
            name = "sliding_numbers_db"
        ).build()

    @Singleton
    @Provides
    fun provideGameResultsDao(appDatabase: AppDatabase) =
        appDatabase.gameResultDao()
}
