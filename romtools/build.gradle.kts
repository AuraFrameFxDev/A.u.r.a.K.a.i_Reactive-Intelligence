import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id("com.android.library")

}

android {
    compileSdk = 36
    defaultConfig {
    }

    buildFeatures {
        compose = true
    }

    // Ensure compose compiler extension version is set so @Composable and related types are generated
    composeOptions {
        // User suggested 1.8.2 may work on bleeding edge toolchains
        kotlinCompilerExtensionVersion = "1.8.2"
    }


    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }

    val romToolsOutputDirectory: DirectoryProperty =
        project.objects.directoryProperty().convention(layout.buildDirectory.dir("rom-tools"))

dependencies {
    api(project(":core-module"))
    implementation(project(":secure-comm"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.coroutines)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.hilt.compiler) // <-- FIXED
    implementation(libs.bundles.network)
    implementation(libs.androidx.room.runtime)
    implementation(libs.hilt.compiler) // <-- FIXED
    implementation(libs.timber)
    implementation(libs.coil.compose)
    debugImplementation(libs.leakcanary.android)
    debugImplementation(libs.androidx.compose.ui.tooling)
    testImplementation(libs.bundles.testing.unit)
    testImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockk.android)
    testImplementation(libs.hilt.android)
    androidTestImplementation(libs.hilt.android)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    // androidTestImplementation(libs.hilt.android.testing); kspAndroidTest(libs.hilt.compiler)
    implementation(kotlin("stdlib-jdk8"))
}

// Copy task
    tasks.register<Copy>("copyRomTools") {
        from("src/main/resources")
        into(romToolsOutputDirectory)
        include("**/*.so", "**/*.bin", "**/*.img", "**/*.jar")
        includeEmptyDirs = false
        doFirst { romToolsOutputDirectory.get().asFile.mkdirs(); logger.lifecycle("📁 ROM tools directory: ${romToolsOutputDirectory.get().asFile}") }
        doLast { logger.lifecycle("✅ ROM tools copied to: ${romToolsOutputDirectory.get().asFile}") }
    }

// Verification task
    tasks.register("verifyRomTools") {
        dependsOn("copyRomTools")
    }

    tasks.named("build") { dependsOn("verifyRomTools") }

    tasks.register("romStatus") {
        group = "aegenesis"; doLast { println("🛠️ ROM TOOLS - Ready (Java 24)") }
    }

// Add modern documentation task that doesn't rely on deprecated plugins
    tasks.register("generateApiDocs") {
        group = "documentation"
        description = "Generates API documentation without relying on deprecated plugins"

        doLast {
            logger.lifecycle("🔍 Generating API documentation for romtools module")
            logger.lifecycle("📂 Source directories:")
            logger.lifecycle("   - ${projectDir.resolve("src/main/kotlin")}")
            logger.lifecycle("   - ${projectDir.resolve("src/main/java")}")

            // Using layout.buildDirectory instead of deprecated buildDir property
            val docsDir = layout.buildDirectory.dir("docs/api").get().asFile
            docsDir.mkdirs()

            val indexFile = docsDir.resolve("index.html")
            indexFile.writeText(
                """
        <!DOCTYPE html>
        <html>
        <head>
            <title>ROM Tools API Documentation</title>
            <style>
                body { font-family: Arial, sans-serif; margin: 20px; }
                h1 { color: #4285f4; }
            </style>
        </head>
        <body>
            <h1>ROM Tools API Documentation</h1>
            <p>Generated on ${
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                }</p>
            <p>JDK Version: 24</p>
            <h2>Module Overview</h2>
            <p>System modification and ROM tools for the A.U.R.A.K.A.I. platform.</p>
        </body>
        </html>
    """.trimIndent()
            )

            logger.lifecycle("✅ Documentation generated at: ${indexFile.absolutePath}")
        }
    }

    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
        }
    }
}

// Top-level dependencies block - moved from inside android {}
dependencies {
    api(project(":core-module"))
    implementation(project(":secure-comm"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.coroutines)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.hilt.compiler) // <-- FIXED
    implementation(libs.bundles.network)
    implementation(libs.androidx.room.runtime)
    implementation(libs.hilt.compiler) // <-- FIXED
    implementation(libs.timber)
    implementation(libs.coil.compose)
    debugImplementation(libs.leakcanary.android)
    debugImplementation(libs.androidx.compose.ui.tooling)
    testImplementation(libs.bundles.testing.unit)
    testImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockk.android)
    testImplementation(libs.hilt.android)
    androidTestImplementation(libs.hilt.android)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    // androidTestImplementation(libs.hilt.android.testing); kspAndroidTest(libs.hilt.compiler)
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.2.20")
    // Use the compose material icons alias from the version catalog
    implementation(libs.hilt.android)
    implementation(libs.hilt.compiler) // <-- FIXED

    // keep the small extra items that were previously at the file bottom
}

// Configure Kotlin compile options globally (set JVM target and add recommended free compiler args)
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        // Set jvm target to JVM 24
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
        // Add the suggested free compiler args
        freeCompilerArgs.addAll(listOf(
            "-Xjvm-default=all",
            "-Xopt-in=kotlin.RequiresOptIn"
        ))
    }
}
