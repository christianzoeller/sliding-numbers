package christianzoeller.slidingnumbers.ui.extensions

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.defaultScreenPadding() =
    this.padding(horizontal = 24.dp, vertical = 48.dp)

@Composable
fun Modifier.defaultScreenModifier(
    contentPadding: PaddingValues,
    scrollState: ScrollState = rememberScrollState()
) =
    this.verticalScroll(scrollState)
        .padding(contentPadding)
        .fillMaxSize()
        .defaultScreenPadding()