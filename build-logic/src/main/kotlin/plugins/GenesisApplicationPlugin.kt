/**
 * ===================================================================
 * GENESIS APPLICATION CONVENTION PLUGIN
 * ===================================================================
 *
 * The primary convention plugin for Android application modules.
 *
 * Plugin Application Order (Critical!):
 * 1. Android Application
 * 2. Hilt (Dependency Injection)
 * 3. KSP (Annotation Processing)
 * 4. Compose Compiler
 * 5. Google Services (Firebase)
 *
 * @since Genesis Protocol 1.0
 */
// build-logic/src/main/kotlin/GenesisApplicationPlugin.kt

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.Actions.with
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.targets.js.binaryen.BinaryenRootPlugin.Companion.apply
import kotlin.apply

class GenesisApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // ===== STEP 1: ANDROID APPLICATION PLUGIN =====
            apply("com.android.application")

            // ===== STEP 2: HILT & OTHER PLUGINS =====
            with(plugins) {
                apply("genesis.android.hilt")
                apply("com.google.devtools.ksp")
                apply("com.google.dagger.hilt.android")
                apply("com.google.dagger.hilt.android.plugin")
                apply("com.google.dagger.hilt.android.compiler")
                apply("com.google.gms.google-services")
            }
        }
    }
}
