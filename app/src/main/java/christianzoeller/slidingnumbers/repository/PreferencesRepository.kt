package christianzoeller.slidingnumbers.repository

import android.content.Context
import android.content.res.Configuration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

private const val uiModeKey = "uiMode"

class PreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val uiMode = intPreferencesKey(uiModeKey)

    val uiModeFlow: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[uiMode] ?: Configuration.UI_MODE_NIGHT_UNDEFINED
        }

    suspend fun changeUiModePreference(newMode: Int) {
        require(uiModeValues.contains(newMode))

        context.dataStore.edit { preferences ->
            preferences[uiMode] = newMode
        }
    }
}

private val uiModeValues = listOf(
    Configuration.UI_MODE_NIGHT_YES,
    Configuration.UI_MODE_NIGHT_NO,
    Configuration.UI_MODE_NIGHT_UNDEFINED
)