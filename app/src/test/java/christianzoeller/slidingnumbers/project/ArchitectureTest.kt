package christianzoeller.slidingnumbers.project

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import org.junit.Test

class ArchitectureTest {
    @Test
    fun `architecture layers have correct dependencies`() {
        Konsist
            .scopeFromProduction()
            .assertArchitecture {
                datasourceLayer.dependsOnNothing()
                repositoryLayer.dependsOn(datasourceLayer)
                featureLayer.dependsOn(repositoryLayer)
            }
    }
}