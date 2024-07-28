package christianzoeller.slidingnumbers.project

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withAnnotation
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

class CorrectnessTest {
    @Test
    fun `screen content composables do not have a modifier parameter but accept content padding`() {
        Konsist.scopeFromProject()
            .functions()
            .withAnnotation { annotation -> annotation.name == "Composable" }
            .withNameEndingWith(screenContentComposableSuffix)
            .run {
                assertTrue { function ->
                    function.hasParameter { parameter ->
                        parameter.hasTypeOf(PaddingValues::class) &&
                                parameter.name == contentPaddingParameterName
                    }
                }
                assertFalse { function ->
                    function.hasParameter { parameter ->
                        parameter.hasTypeOf(Modifier::class)
                    }
                }
            }
    }
}