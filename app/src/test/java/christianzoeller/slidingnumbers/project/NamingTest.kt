package christianzoeller.slidingnumbers.project

import androidx.lifecycle.ViewModel
import christianzoeller.slidingnumbers.project.extensions.topLevelScreenComposables
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withAllParentsOf
import com.lemonappdev.konsist.api.ext.list.withPackage
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

class NamingTest {
    @Test
    fun `top-level screen composables have correct suffix`() {
        Konsist.scopeFromProject()
            .topLevelScreenComposables()
            .assertTrue { it.name.endsWith(topLevelScreenComposableSuffix) }
    }

    @Test
    fun `view models have correct suffix`() {
        Konsist.scopeFromProject()
            .classes()
            .withAllParentsOf(ViewModel::class)
            .assertTrue { it.name.endsWith(viewModelSuffix) }
    }

    @Test
    fun `repositories have correct suffix`() {
        Konsist.scopeFromProject()
            .classes()
            .withPackage(repositoryPackage)
            .assertTrue { it.name.endsWith(repositorySuffix) }
    }
}