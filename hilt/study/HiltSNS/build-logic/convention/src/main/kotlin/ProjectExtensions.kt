import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

val Project.libraries get(): VersionCatalog = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")