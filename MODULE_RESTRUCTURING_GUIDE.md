# 🔧 Module Build Files - Complete Restructuring Guide

## Changes Made

### ✅ Fixed Files

1. **app/build.gradle.kts** - Now uses `genesis.android.application`
2. **feature-module/build.gradle.kts** - Now uses `genesis.android.library`
3. **core-module/build.gradle.kts** - Now uses `genesis.jvm` + `genesis.android.base`
4. **data/api/build.gradle.kts** - Now uses `genesis.jvm` + `genesis.android.base` +
   `genesisopenapi.generator`
5. **gradle/libs.versions.toml** - Added all Genesis plugins and missing library entries

### 📋 Modules Needing Updates

#### Android Library Modules (use `genesis.android.library`)

- sandbox-ui
- romtools
- collab-canvas
- colorblendr
- datavein-oracle-native
- oracle-drive-integration
- secure-comm
- module-a through module-f
- benchmark

#### JVM Modules (use `genesis.jvm`)

- list (already mostly correct)
- utilities (if exists)

## Standard Patterns

### For Android Application:

```kotlin
plugins {
    alias(libs.plugins.genesis.android.application)
}

android {
    namespace = "your.package.name"
    
    defaultConfig {
        applicationId = "your.package.name"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    // Hilt (auto-configured by convention plugin)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    
    // Your other dependencies...
}
```

### For Android Library:

```kotlin
plugins {
    alias(libs.plugins.genesis.android.library)
}

android {
    namespace = "your.package.name"
}

dependencies {
    // Hilt (auto-configured by convention plugin)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    
    // Your other dependencies...
}
```

### For JVM Module:

```kotlin
plugins {
    alias(libs.plugins.genesis.jvm)
}

dependencies {
    // Your dependencies...
}
```

## Key Changes

### Plugin Order (Auto-handled by Genesis plugins):

- ❌ **DON'T** manually apply: `com.android.library`, `kotlin("android")`, `ksp`, etc.
- ✅ **DO** use Genesis convention plugins that apply them in correct order

### KSP Usage:

- ❌ **DON'T** use: `add("ksp", ...)`
- ✅ **DO** use: `ksp(...)`

### Removed Configurations:

- compileSdk (handled by convention plugin)
- minSdk (handled by convention plugin)
- compileOptions (handled by convention plugin)
- buildFeatures.compose (handled by convention plugin)
- java toolchain (handled by convention plugin)

### Kept Configurations:

- namespace (module-specific)
- applicationId (app only)
- versionCode/versionName (app only)
- Custom build logic

## What Genesis Plugins Provide

### `genesis.android.application` provides:

- ✅ com.android.application
- ✅ Hilt DI setup
- ✅ KSP configuration
- ✅ Compose Compiler
- ✅ Google Services (Firebase)
- ✅ compileSdk/minSdk/targetSdk from catalog
- ✅ Java 24 configuration
- ✅ ProGuard setup
- ✅ Core library desugaring
- ✅ Packaging options
- ✅ Lint configuration

### `genesis.android.library` provides:

- ✅ com.android.library
- ✅ Hilt DI setup
- ✅ KSP configuration
- ✅ Compose Compiler
- ✅ compileSdk/minSdk from catalog
- ✅ Java 25/24 configuration
- ✅ Compose buildFeature
- ✅ Packaging options
- ✅ Lint configuration

### `genesis.android.base` provides:

- ✅ Kotlin Serialization
- ✅ Native support (placeholder)

### `genesis.jvm` provides:

- ✅ kotlin-jvm plugin
- ✅ Java 25/24 configuration
- ✅ Kotlin toolchain
- ✅ Context receivers
- ✅ Compiler optimizations

### `genesisopenapi.generator` provides:

- ✅ OpenAPI Generator 7.16.0
- ✅ Kotlin + Retrofit2 generation
- ✅ kotlinx.serialization support
- ✅ Hilt integration
- ✅ Clean task for generated code

## Migration Steps

1. ✅ Replace plugin block with Genesis convention plugin
2. ✅ Remove auto-configured settings (compileSdk, minSdk, etc.)
3. ✅ Keep namespace and app-specific configs
4. ✅ Change `add("ksp", ...)` to `ksp(...)`
5. ✅ Remove duplicate Kotlin stdlib dependencies
6. ✅ Clean up custom tasks if redundant

## Next Steps

Run the batch update script to fix all remaining modules automatically.
