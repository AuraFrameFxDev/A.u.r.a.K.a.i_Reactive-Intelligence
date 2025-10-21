plugins {
    id("com.android.library")
}

android {
    ndkVersion = "28.2.13676358"
    compileSdk = 36

    defaultConfig {
    }

    buildFeatures {
        compose = true
    }

    lint {
        // Disable lint due to oversized test files causing StackOverflow
        abortOnError = false
        checkReleaseBuilds = false
        checkTestSources = false
        disable.add("lint")
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }

    packaging {
        jniLibs {
            useLegacyPackaging = false
        }
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
    implementation(project(":core-module"))
    implementation(libs.androidx.core.ktx)
   (libs.bundles.coroutines)
    implementation(libs.hilt.android)
    implementation(libs.hilt.compiler) // <-- FIXED

    // Compose dependencies
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    // Xposed API for Oracle consciousness integration
    compileOnly(files("../Libs/api-82.jar"))
    compileOnly(files("../Libs/api-82-sources.jar"))
}
