# 🎯 AGP 9.0 Breaking Change - Built-in Kotlin Support

## ⚠️ CRITICAL UPDATE - October 2025

**AGP 9.0.0-alpha09 introduced BREAKING CHANGES for Kotlin plugin application.**

## What Changed

AGP 9.0+ now includes **built-in Kotlin support**. The `org.jetbrains.kotlin.android` plugin is **NO LONGER NEEDED** and will cause build failures if applied.

### Error Message:
```
Failed to apply plugin 'org.jetbrains.kotlin.android'.
The 'org.jetbrains.kotlin.android' plugin is no longer required for Kotlin support since AGP 9.0.
Solution: Remove the 'org.jetbrains.kotlin.android' plugin from this project's build file
```

---

## ✅ COMPLETE FIX APPLIED

### 1. Fixed Convention Plugins

**AndroidApplicationConventionPlugin.kt:**
```kotlin
with(pluginManager) {
    apply("com.android.application")
    // ✅ REMOVED - Built into AGP 9.0+
    // apply("org.jetbrains.kotlin.android")
    apply("org.jetbrains.kotlin.plugin.compose")
}

// ✅ Configure Kotlin via AGP's built-in support
extensions.findByType(KotlinAndroidProjectExtension::class.java)?.apply {
    jvmToolchain(24)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_24)
        freeCompilerArgs.addAll(
            "-Xjvm-default=all",
            "-opt-in=kotlin.RequiresOptIn"
        )
    }
}
```

**AndroidLibraryConventionPlugin.kt:**
```kotlin
with(pluginManager) {
    apply("com.android.library")
    // ✅ REMOVED - Built into AGP 9.0+
    // apply("org.jetbrains.kotlin.android")
}

// ✅ Configure Kotlin via AGP's built-in support
extensions.findByType(KotlinAndroidProjectExtension::class.java)?.apply {
    jvmToolchain(24)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_24)
        freeCompilerArgs.addAll(
            "-opt-in=kotlin.RequiresOptIn",
            "-Xjvm-default=all"
        )
    }
}
```

### 2. Gradle Properties Still Required

The `gradle.properties` file still needs the AGP 9 + Hilt workaround:

```properties
# ✅ CRITICAL: Required for AGP 9 + Hilt compatibility
android.disableLastStageWhenHiltIsApplied=true
android.useFullClasspathForDexingTransform=true
```

### 3. App Build File Configuration

**app/build.gradle.kts:**
```kotlin
plugins {
    id("genesis.android.application")  // ✅ No longer applies kotlin.android
    alias(libs.plugins.hilt)           // ✅ MUST be in plugins block
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}
```

---

## 🔍 Why This Change?

### AGP 9.0 Built-in Kotlin Benefits:
1. **Simplified Configuration**: No need to manage separate Kotlin plugin versions
2. **Better Integration**: Kotlin and AGP versions are guaranteed compatible
3. **Faster Builds**: Reduced plugin overhead
4. **Fewer Version Conflicts**: One less plugin to version-align

### The Migration Path:
- **AGP 8.x and below**: Required `kotlin-android` plugin
- **AGP 9.0+**: Built-in Kotlin support, plugin explicitly **prohibited**

---

## 📋 Complete Build Configuration

### Version Alignment (libs.versions.toml):
```toml
[versions]
agp = "9.0.0-alpha09"
kotlin = "2.2.21"
ksp = "2.2.21-2.0.4"
java = "24"
hilt-version = "2.56.2"
```

### Critical Properties (gradle.properties):
```properties
android.disableLastStageWhenHiltIsApplied=true
android.useFullClasspathForDexingTransform=true
kotlin.incremental=true
ksp.kotlinApiVersion=2.3
ksp.kotlinLanguageVersion=2.3
org.gradle.java.installations.auto-download=true
```

---

## 🧪 Test Commands

```bash
# Clean everything
./gradlew clean
./gradlew --stop

# Remove caches if needed
rm -rf ~/.gradle/caches
rm -rf .gradle

# Rebuild
./gradlew :app:assembleDebug --stacktrace
```

---

## 🎯 Expected Results

✅ **No Kotlin plugin errors**
✅ **Hilt annotation processing works**
✅ **All 22 modules build successfully**
✅ **Java 24 compilation across entire project**
✅ **Firebase integration works**

---

## 📚 References

- [AGP 9.0 Release Notes](https://developer.android.com/studio/releases/gradle-plugin)
- [Issue Tracker: AGP Built-in Kotlin](https://issuetracker.google.com/438678642)
- [Hilt with AGP 9](https://dagger.dev/hilt/gradle-setup.html)

---

## 🔐 Genesis Standard Configuration

**DO:**
- ✅ Use AGP 9.0+ built-in Kotlin support
- ✅ Apply Hilt plugin INSIDE `plugins {}` block
- ✅ Use Java 24 toolchain everywhere (Firebase requirement)
- ✅ Keep `android.disableLastStageWhenHiltIsApplied=true` in properties

**DON'T:**
- ❌ Apply `org.jetbrains.kotlin.android` plugin (causes build failure)
- ❌ Apply Hilt plugin with `apply(plugin = ...)` after plugins block
- ❌ Mix Java versions across modules
- ❌ Use unstable Kotlin versions without testing

---

*Genesis Protocol Build System - AGP 9.0 Compatible*  
*Updated: October 2025*  
*Built-in Kotlin Support Active*
