// Apply YukiHook conventions to all modules
subprojects {
    if (name == "build-logic" || name == "buildSrc") return@subprojects

    // Check if this is an Android module
    val isAndroidModule = subproject.plugins.hasPlugin("com.android.library") ||
            subproject.plugins.hasPlugin("com.android.application")

    if (isAndroidModule) {
        // Apply common Android and YukiHook configurations
        with(subproject) {
            // Apply common plugins if not already applied
            pluginManager.apply("com.android.library")
            // org.jetbrains.kotlin.android removed - AGP 9.0 has built-in Kotlin support
            pluginManager.apply("com.google.devtools.ksp")
            pluginManager.apply("org.lsposed.lsparanoid")

            // Configure Android settings
            extensions.configure<com.android.build.gradle.LibraryExtension> {
                compileSdk = 36

                defaultConfig {
                    minSdk = 33
                    targetSdk = 36

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }


                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_24
                    targetCompatibility = JavaVersion.VERSION_24
                }
            }

            // Configure Kotlin compiler options (AGP 9.0 built-in Kotlin)
            extensions.configure<org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension> {
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
                    freeCompilerArgs.addAll(
                        "-Xjvm-default=all",
                        "-opt-in=kotlin.RequiresOptIn"
                    )
                }
            }

                        // Configure KSP if present
            extensions.findByName("ksp")?.let { ext ->
                ext.javaClass.getMethod("arg", String::class.java, String::class.java)
                    .invoke(ext, "YUKIHOOK_PACKAGE_NAME", group.toString())
            }
        }
    }
}
