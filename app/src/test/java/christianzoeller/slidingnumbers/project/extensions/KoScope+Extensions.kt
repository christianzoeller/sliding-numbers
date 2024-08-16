package christianzoeller.slidingnumbers.project.extensions

import androidx.compose.runtime.Composable
import christianzoeller.slidingnumbers.project.viewModelSuffix
import com.lemonappdev.konsist.api.container.KoScope
import com.lemonappdev.konsist.api.ext.list.withAnnotation
import com.lemonappdev.konsist.api.ext.list.withParameter

fun KoScope.composableFunctions() =
    functions().withAnnotation { annotation ->
        annotation.name == Composable::class.simpleName
    }

fun KoScope.topLevelScreenComposables() =
    // Here, we define top-level screen composables as those
    // composables that have a view model parameter
    this.composableFunctions()
        .withParameter { parameter ->
            parameter.hasType { type -> type.hasNameEndingWith(viewModelSuffix) }
        }