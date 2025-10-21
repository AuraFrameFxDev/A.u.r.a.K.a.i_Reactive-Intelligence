// ==== GENESIS PROTOCOL - ORACLE DRIVE INTEGRATION ====
// AI storage module using convention plugins

plugins {
    id("com.android.library")
}

android {
    compileSdk = 36
    defaultConfig {
    }
    ndkVersion = "29.0.14206865"
    experimentalProperties["android.ndk.suppressMinSdkVersionError"] = 21

    // Ensure AGP knows where this module's CMakeLists lives
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }
}

dependencies {
    implementation(project(":core-module"))
    implementation(project(":secure-comm"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.coroutines)
    implementation(libs.hilt.android)
    implementation(libs.hilt.compiler) // <-- FIXED
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.2.20")
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}
