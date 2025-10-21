plugins {
    id("com.android.library")
}

android {
    namespace = "dev.aurakai.auraframefx.collab-canvas"
    compileSdk = 36

    defaultConfig {
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }
}
dependencies {
    // Core
    implementation(project(":core-module"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)

    // Compose
    implementation(platform(libs.androidx.compose.bom))

    // Lifecycle
    implementation(libs.bundles.lifecycle)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.compiler)

    // Utilities
    implementation(libs.timber)

    // Testing
    testImplementation(libs.junit4)
    androidTestImplementation(libs.bundles.testing.android)
}
