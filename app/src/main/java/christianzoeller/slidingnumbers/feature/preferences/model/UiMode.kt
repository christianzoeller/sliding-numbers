package christianzoeller.slidingnumbers.feature.preferences.model

import android.content.res.Configuration
import androidx.annotation.StringRes
import christianzoeller.slidingnumbers.R

sealed class UiMode(
    val value: Int,
    @StringRes val label: Int
) {
    companion object {
        fun getByValue(value: Int) = when (value) {
            Configuration.UI_MODE_NIGHT_YES -> Dark
            Configuration.UI_MODE_NIGHT_NO -> Light
            Configuration.UI_MODE_NIGHT_UNDEFINED -> System
            else -> null
        }

        val all = listOf(Light, Dark, System)
    }

    data object Light : UiMode(
        value = Configuration.UI_MODE_NIGHT_NO,
        label = R.string.preferences_ui_mode_light
    )

    data object Dark : UiMode(
        value = Configuration.UI_MODE_NIGHT_YES,
        label = R.string.preferences_ui_mode_dark
    )

    data object System : UiMode(
        value = Configuration.UI_MODE_NIGHT_UNDEFINED,
        label = R.string.preferences_ui_mode_system
    )
}