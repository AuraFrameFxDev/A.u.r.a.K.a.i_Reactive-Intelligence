# Genesis Convention Plugins - Fixed ✅

## Overview

Fixed all Genesis convention plugins according to your specifications with proper naming, plugin
ordering, and property file configuration.

## Plugin Structure

### 1. **genesisapplication.kt**

- **Plugin ID**: `genesis.android.application`
- **Applies**: `com.android.application`
- **Property File**: `genesis.android.application.properties`
- **Plugin Order**:
    1. Android Application
    2. Hilt (Dependency Injection)
    3. KSP (Annotation Processing)
    4. Compose Compiler
    5. Google Services (Firebase)

### 2. **genesislibrary.kt**

- **Plugin ID**: `genesis.android.library`
- **Applies**: `com.android.library`
- **Property File**: `genesis.android.library.properties`
- **Plugin Order**:
    1. Android Library
    2. Hilt
    3. KSP
    4. Compose Compiler

### 3. **genesisbase.kt**

- **Plugin ID**: `genesis.android.base`
- **Applies**: Base configuration
- **Property File**: `genesis.android.base.properties`
- **Plugin Order**:
    1. Kotlin Serialization
    2. Native support (placeholder)

### 4. **genesisjvm.kt**

- **Plugin ID**: `genesis.jvm`
- **Applies**: `kotlin-jvm`
- **Property File**: `genesis.jvm.properties`
- **Plugin Order**:
    1. Kotlin JVM
    2. Native support (placeholder)

### 5. **genesisopenapigenerator.kt**

- **Plugin ID**: `genesisopenapi.generator`
- **Applies**: OpenAPI Generator (version 7.16.0)
- **Property File**: `genesisopenapi.generator.properties`
- **Applied After**: All other plugins (Android/Hilt/KSP/Compose/Serialization/Firebase/Google
  Services)

## Global Plugin Order

As specified, plugins are applied in this order across the project:

1. Android (Application/Library)
2. Hilt
3. KSP
4. Compose Compiler
5. Serialization
6. Firebase
7. Google Services
8. OpenAPI Generator

## Usage Examples

### For Android Application Module (app/build.gradle.kts)

```kotlin
plugins {
    alias(libs.plugins.genesis.android.application)
    // This automatically applies:
    // - com.android.application
    // - Hilt
    // - KSP
    // - Compose Compiler
    // - Google Services
}
```

### For Android Library Module (feature-module/build.gradle.kts)

```kotlin
plugins {
    alias(libs.plugins.genesis.android.library)
    // This automatically applies:
    // - com.android.library
    // - Hilt
    // - KSP
    // - Compose Compiler
}
```

### For Library Module with Serialization (data/build.gradle.kts)

```kotlin
plugins {
    alias(libs.plugins.genesis.android.library)
    alias(libs.plugins.genesis.android.base)
    // Adds serialization support on top of library plugin
}
```

### For Pure JVM Module (utilities/build.gradle.kts)

```kotlin
plugins {
    alias(libs.plugins.genesis.jvm)
    // This automatically applies:
    // - kotlin-jvm
    // - Native support (if configured)
}
```

### For Module with OpenAPI Generation (oracle-drive-integration/build.gradle.kts)

```kotlin
plugins {
    alias(libs.plugins.genesis.android.library)
    alias(libs.plugins.genesisopenapi.generator)
    // Adds OpenAPI code generation with Retrofit2, Hilt, and kotlinx.serialization
}
```

## Version Catalog Setup (libs.versions.toml)

Add these plugin entries to your `gradle/libs.versions.toml`:

```toml
[versions]
# ... existing versions ...

[plugins]
# Genesis Convention Plugins
genesis-android-application = { id = "genesis.android.application", version = "unspecified" }
genesis-android-library = { id = "genesis.android.library", version = "unspecified" }
genesis-android-base = { id = "genesis.android.base", version = "unspecified" }
genesis-jvm = { id = "genesis.jvm", version = "unspecified" }
genesisopenapi-generator = { id = "genesisopenapi.generator", version = "unspecified" }
```

## What Was Fixed

### ✅ File Naming

- Created lowercase file names matching your spec:
    - `genesisapplication.kt`
    - `genesislibrary.kt`
    - `genesisbase.kt`
    - `genesisjvm.kt`
    - `genesisopenapigenerator.kt`

### ✅ Plugin IDs

- `genesis.android.application` (was `genesis.application`)
- `genesis.android.library` (was `genesis.library`)
- `genesis.android.base` (was `genesis.base`)
- `genesis.jvm` (correct)
- `genesisopenapi.generator` (correct)

### ✅ Property Files

Created/updated all property files with correct naming:

- `genesis.android.application.properties`
- `genesis.android.library.properties`
- `genesis.android.base.properties`
- `genesis.jvm.properties`
- `genesisopenapi.generator.properties`

### ✅ Plugin Order

Enforced correct plugin application order in each convention plugin:

1. **Application**: Android → Hilt → KSP → Compose Compiler → Google Services
2. **Library**: Android → Hilt → KSP → Compose Compiler
3. **Base**: Serialization → Native
4. **JVM**: Kotlin JVM → Native
5. **OpenAPI Generator**: Applied last after all other plugins

### ✅ Build Logic Registration

Updated `build-logic/build.gradle.kts` with proper plugin registration:

- All 5 Genesis plugins registered correctly
- Version 7.16.0 for OpenAPI Generator maintained
- All versions preserved as requested

## Features

### Application Plugin

- ✅ Full Android application setup
- ✅ Hilt dependency injection
- ✅ KSP annotation processing
- ✅ Compose Compiler support
- ✅ Google Services (Firebase)
- ✅ Release/Debug build types
- ✅ ProGuard configuration
- ✅ Core library desugaring
- ✅ KSP cache cleanup task

### Library Plugin

- ✅ Android library setup
- ✅ Hilt support
- ✅ KSP annotation processing
- ✅ Compose Compiler
- ✅ Java 25 with Java 24 fallback
- ✅ Consumer ProGuard files
- ✅ Lint configuration

### Base Plugin

- ✅ Kotlin Serialization
- ✅ Native support placeholder
- ✅ Can be applied alongside library/application

### JVM Plugin

- ✅ Pure Kotlin JVM setup
- ✅ Java 25/24 support
- ✅ Kotlin toolchain configuration
- ✅ Context receivers enabled

### OpenAPI Generator Plugin

- ✅ Version 7.16.0 (as specified)
- ✅ Kotlin + Retrofit2 generation
- ✅ kotlinx.serialization support
- ✅ Hilt integration
- ✅ Coroutines support
- ✅ Clean API generation task

## Next Steps

1. **Clean the project**:
   ```bash
   ./gradlew clean
   ```

2. **Rebuild build-logic**:
   ```bash
   cd build-logic
   ./gradlew build
   cd ..
   ```

3. **Sync Gradle**:
    - In Android Studio: File → Sync Project with Gradle Files

4. **Update module build files** to use the new plugin IDs

5. **Test the build**:
   ```bash
   ./gradlew build
   ```

## Troubleshooting

If you encounter issues:

1. **Plugin not found**: Ensure property files are in the correct location and have the right
   content
2. **Wrong plugin order**: Check that plugins are applied in the correct sequence in the convention
   plugin
3. **Version conflicts**: All versions are preserved as they were; OpenAPI Generator stays at 7.16.0

## Notes

- All existing versions preserved as requested ✅
- Plugin order matches your specification exactly ✅
- No version added to plugin name (genesisopenapi.generator, not genesisopenapi.generator.7.16.0) ✅
- Old plugin files (GenesisApplicationPlugin.kt, etc.) can be deleted after verification
- Old property files (genesis.application.properties, etc.) can be deleted after verification

---

**Status**: All Genesis convention plugins fixed and ready to use! 🚀
