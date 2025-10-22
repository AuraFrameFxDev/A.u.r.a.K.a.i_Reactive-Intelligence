rootProject.name = "build-logic"

// Configure the build-logic project itself
pluginManagement {
    // Include 'build-logic' build to the composite build
    includeBuild("../build-logic")
    
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
