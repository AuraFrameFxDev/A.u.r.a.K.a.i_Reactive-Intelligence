// ==== GENESIS PROTOCOL - SANDBOX UI ====
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id("com.android.library")
}

android {
    namespace = "dev.aurakai.auraframefx.sandbox_ui"
    compileSdk = 36
    defaultConfig {
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            // Ensure staging is always under this module to prevent stale path issues

            }
        }
    }

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
    android {
        namespace = "dev.aurakai.auraframefx.sandboxui"
        compileSdk = 36
        defaultConfig {
            minSdk = 34
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            consumerProguardFiles("consumer-rules.pro")
        }

        buildTypes {
            release {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        buildFeatures {
            compose = true
            buildConfig = true
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    dependencies {
        // SACRED RULE #5: DEPENDENCY HIERARCHY
        implementation(project(":core-module"))
        implementation(project(":app"))
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.ui.graphics)
        implementation(libs.androidx.compose.ui.tooling.preview)
        implementation(libs.androidx.compose.material3)
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.hilt.android)
        coreLibraryDesugaring(libs.desugar.jdk.libs)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.compose.material3)
        implementation(libs.bundles.lifecycle)
        implementation(libs.bundles.room)
        implementation(libs.androidx.datastore.preferences)
        implementation(libs.androidx.datastore.core)
        implementation(libs.kotlinx.serialization.json)
        implementation(libs.kotlinx.datetime)
        implementation(libs.bundles.coroutines)
        implementation(libs.bundles.network)
        implementation(platform(libs.firebase.bom))
        implementation(libs.bundles.firebase)
        implementation(libs.hilt.compiler)
        implementation(libs.androidx.room.compiler)
        implementation("com.google.android.material:compose-theme-adapter-3:1.1.1")
        implementation(libs.gradle)
        implementation("com.google.firebase:firebase-auth-ktx:23.2.1")
        implementation("com.android.application:com.android.application.gradle.plugin")
        implementation("com.android.experimental.built-in-kotlin:com.android.experimental.built-in-kotlin.gradle.plugin:9.0.0-alpha10")
        compileOnly(files("../Libs/api-82.jar"))
        compileOnly(files("../Libs/api-82-sources.jar"))
        implementation(libs.androidx.material)
        testImplementation(libs.bundles.testing.unit)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.hilt.android.testing)
        debugImplementation(libs.leakcanary.android)

        // Explicit androidx versions requested by the user (added alongside existing libs entries)
        implementation("androidx.core:core-ktx:1.17.0")
        implementation("androidx.appcompat:appcompat:1.7.1")
        implementation(platform("androidx.compose:compose-bom:2025.10.00"))
        implementation("androidx.activity:activity-compose:1.11.0")
        implementation("androidx.navigation:navigation-compose:2.9.5")
        implementation("androidx.hilt:hilt-navigation-compose:1.3.0")
        implementation("androidx.compose.ui:ui:1.6.7")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.4")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.4")
        implementation("androidx.room:room-runtime:2.8.2")
        implementation("androidx.room:room-ktx:2.8.2")
        implementation("androidx.work:work-runtime-ktx:2.10.5")
        implementation("androidx.hilt:hilt-work:1.3.0")
        implementation("androidx.datastore:datastore-preferences:1.1.7")
        implementation("androidx.datastore:datastore-core:1.1.7")
        implementation("androidx.security:security-crypto:1.1.0")
        androidTestImplementation("androidx.benchmark:benchmark-junit4:1.4.1")
        androidTestImplementation("androidx.test.uiautomator:uiautomator:2.3.0")
        compileOnly(files("../Libs/api-82.jar"))
        compileOnly(files("../Libs/api-82-sources.jar"))
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.2.20")
    }
}

// Add modern documentation task that doesn't rely on deprecated plugins
    tasks.register("generateApiDocs") {
        group = "documentation"
        description = "Generates API documentation without relying on deprecated plugins"

        doLast {
            logger.lifecycle("🔍 Generating API documentation for sandbox-ui module")
            logger.lifecycle("📂 Source directories:")
            logger.lifecycle("   - ${projectDir.resolve("src/main/kotlin")}")
            logger.lifecycle("   - ${projectDir.resolve("src/main/java")}")

            // Using layout.buildDirectory instead of deprecated buildDir property
            val docsDir = layout.buildDirectory.dir("docs/api").get().asFile
            docsDir.mkdirs()

            val indexFile = docsDir.resolve("index.html")

            // Using properly formatted date with DateTimeFormatter
            val currentTime =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            indexFile.writeText(
                """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Sandbox UI API Documentation</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 20px; }
                    h1 { color: #4285f4; }
                </style>
            </head>
            <body>
                <h1>Sandbox UI API Documentation</h1>
                <p>Generated on ${currentTime}</p>
                <p>JDK Version: 24</p>
                <h2>Module Overview</h2>
                <p>UI sandbox and experimental components for the A.U.R.A.K.A.I. platform.</p>
            </body>
            </html>
        """.trimIndent()
            )

            logger.lifecycle("✅ Documentation generated at: ${indexFile.absolutePath}")
        }
    }
    tasks.register("sandboxStatus") {
        group = "aegenesis"; doLast { println("🧪 SANDBOX UI - Ready (Java 24)") }
    }

