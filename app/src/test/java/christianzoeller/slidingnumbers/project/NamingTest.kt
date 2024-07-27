package christianzoeller.slidingnumbers.project

import androidx.lifecycle.ViewModel
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withAllParentsOf
import com.lemonappdev.konsist.api.ext.list.withPackage
import com.lemonappdev.konsist.api.ext.list.withParameter
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

// TODO move to some central place
private const val repositoryPackage = "christianzoeller.slidingnumbers.repository"

class NamingTest {
    @Test
    fun `classes extending 'ViewModel' should have 'ViewModel' suffix`() {
        Konsist.scopeFromProject()
            .classes()
            .withAllParentsOf(ViewModel::class)
            .assertTrue { it.name.endsWith("ViewModel") }
    }

    @Test
    fun `composables that take a view model should have 'Screen' suffix`() {
        Konsist.scopeFromProject()
            .functions()
            .withParameter { parameter ->
                parameter.hasType { type -> type.hasNameEndingWith("ViewModel") }
            }
            .assertTrue { it.name.endsWith("Screen") }
    }

    @Test
    fun `classes in 'Repository' package should have 'Repository' suffix`() {
        Konsist.scopeFromProject()
            .classes()
            .withPackage(repositoryPackage)
            .assertTrue { it.name.endsWith("Repository") }
    }
}