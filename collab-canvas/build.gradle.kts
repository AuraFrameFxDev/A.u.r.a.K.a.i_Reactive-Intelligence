// Apply plugins (versions via version catalog)
plugins {
    id("com.android.library")
}

android { compileSdk = 36

    dependencies {
        // Module dependencies
        implementation(project(":core-module"))
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

        // Hilt
        implementation(libs.hilt.android)
        implementation(libs.hilt.compiler) // <-- FIXED

        // Coroutines
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.coroutines.android)

        // Network
        implementation(libs.retrofit.converter.kotlinx.serialization)
        implementation(libs.okhttp.logging.interceptor)

        // Room
        implementation(libs.androidx.room.runtime)
        implementation(libs.androidx.room.ktx)
        implementation(libs.androidx.room.compiler) // <-- FIXED

        // Firebase
        implementation(platform(libs.firebase.bom))

        // UI / Utils
        implementation(libs.coil.compose)
        implementation(libs.timber)
        implementation(fileTree("../Libs") { include("*.jar") })
        implementation(libs.gson)

        // Testing
        testImplementation(libs.junit4)
        testImplementation(libs.mockk.android)
        androidTestImplementation(libs.androidx.test.ext.junit)
        androidTestImplementation(libs.androidx.test.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.compose.ui)

        // Debug
        debugImplementation(libs.androidx.compose.ui.tooling)
        debugImplementation(libs.androidx.compose.ui.tooling.preview)

        compileOnly(files("../Libs/api-82.jar"))
        compileOnly(files("../Libs/api-82-sources.jar"))
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.2.20")
    }

    tasks.register("collabStatus") {
        group = "aegenesis"
        doLast { println("COLLAB CANVAS - Ready (Java 24 toolchain, unified).") }
    }
}
