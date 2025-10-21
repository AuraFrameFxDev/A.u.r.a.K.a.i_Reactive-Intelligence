plugins {
    // Add required plugins here
}

android {
    // ...existing code...
    defaultConfig {
        minSdk = 21
    }
    ndkVersion = "29.0.14206865"
    experimentalProperties["android.ndk.suppressMinSdkVersionError"] = 21
    // ...existing code...
}

tasks.register("updateScreenshots") {
    group = "screenshot"
    description = "Update Genesis Protocol UI component screenshots"
    doLast {
        println("📸 Genesis Protocol screenshots update ready")
        println("🎨 Configure screenshot baseline when Paparazzi is available")
    }
}

dependencies {
    // Add dependencies here
}
