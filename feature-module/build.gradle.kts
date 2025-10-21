// ==== GENESIS PROTOCOL - FEATURE MODULE ====
// Primary feature module using basic plugins (avoiding AGP 9.0 Hilt issues)
plugins {
    id("com.android.library")
}

android {
    compileSdk = 36

    defaultConfig {
        multiDexEnabled = true
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
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
        }
    }
}

dependencies {
    api(project(":core-module"))
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

    implementation(libs.hilt.android) // TEMPORARILY REMOVED
    implementation(libs.hilt.compiler) // TEMPORARILY REMOVED
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.network)

    // Networking
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.compiler) // Use implementation for KSP processors in modern Gradle
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    implementation(libs.timber)
    implementation(libs.coil.compose)

    // External libraries
    implementation(fileTree("../Libs") { include("*.jar") })

    // Testing
    testImplementation(libs.bundles.testing.unit)
    testImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockk.android)
    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    // androidTestImplementation(libs.hilt.android.testing) // TEMPORARILY REMOVED
    debugImplementation(libs.leakcanary.android)
    debugImplementation(libs.androidx.compose.ui.tooling)

}

tasks.register("featureStatus") {
    // MOVED to root level and Updated
    group = "aegenesis"
    doLast { println("🚀 FEATURE MODULE - ${android.namespace} - Ready (Java 24)!") }
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).configureEach {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-opt-in=kotlin.RequiresOptIn",
            "-Xjvm-default=all"
        )
    }
}
