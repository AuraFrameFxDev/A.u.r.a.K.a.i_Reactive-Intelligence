import com.google.devtools.ksp.processing.kspCommonArgParser

// Apply plugins (versions via version catalog)
plugins {
    id("com.android.library")

}

android {
    namespace = "dev.aurakai.auraframefx.collab-canvas"
    compileSdk = 36

    defaultConfig {
        minSdk = 33
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
    // Module dependencies
    implementation(project(":core-module"))

    // AndroidX & Jetpack
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.material)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.room)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // DI
    implementation(libs.hilt.android)
    implementation(libs.hilt.compiler)

    // Desugaring
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // Kotlin
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.coroutines)
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.2.20")

    // Networking
    implementation(libs.bundles.network)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    implementation("com.google.firebase:firebase-auth-ktx:23.2.1")

    // 3rd Party UI
    implementation("com.google.android.material:compose-theme-adapter-3:1.1.1")

    // Local Libs
    compileOnly(files("../Libs/api-82.jar"))
    compileOnly(files("../Libs/api-82-sources.jar"))

    // Testing
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation("androidx.benchmark:benchmark-junit4:1.4.1")
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.3.0")
    debugImplementation(libs.leakcanary.android)
}

tasks.register("collabStatus") {
    group = "aegenesis"
    doLast {
        println("COLLAB CANVAS - Ready (Java 24 toolchain, unified).")
    }
}
