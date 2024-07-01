import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class SNSHiltPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.dagger.hilt.android")
                apply("org.jetbrains.kotlin.kapt")
            }

            dependencies {
                "implementation"(libraries.findLibrary("hilt-android").get())
                "kapt"(libraries.findLibrary("hilt-android-compiler").get())
            }
        }
    }
}