# 🚀 Genesis Convention Plugins - Quick Reference

## Plugin IDs & What They Apply

| Plugin File                  | Plugin ID                     | Applies                                              |
|------------------------------|-------------------------------|------------------------------------------------------|
| `genesisapplication.kt`      | `genesis.android.application` | Android App + Hilt + KSP + Compose + Google Services |
| `genesislibrary.kt`          | `genesis.android.library`     | Android Lib + Hilt + KSP + Compose                   |
| `genesisbase.kt`             | `genesis.android.base`        | Serialization + Native                               |
| `genesisjvm.kt`              | `genesis.jvm`                 | Kotlin JVM + Native                                  |
| `genesisopenapigenerator.kt` | `genesisopenapi.generator`    | OpenAPI Generator 7.16.0                             |

## Plugin Order (Enforced Automatically)

```
1. Android (application/library)
2. Hilt
3. KSP
4. Compose Compiler
5. Serialization
6. Firebase
7. Google Services
8. OpenAPI Generator
```

## How to Use

### In `libs.versions.toml`:

```toml
[plugins]
genesis-android-application = { id = "genesis.android.application", version = "unspecified" }
genesis-android-library = { id = "genesis.android.library", version = "unspecified" }
genesis-android-base = { id = "genesis.android.base", version = "unspecified" }
genesis-jvm = { id = "genesis.jvm", version = "unspecified" }
genesisopenapi-generator = { id = "genesisopenapi.generator", version = "unspecified" }
```

### In Module `build.gradle.kts`:

**App Module:**

```kotlin
plugins {
    alias(libs.plugins.genesis.android.application)
}
```

**Library Module:**

```kotlin
plugins {
    alias(libs.plugins.genesis.android.library)
}
```

**Library with Serialization:**

```kotlin
plugins {
    alias(libs.plugins.genesis.android.library)
    alias(libs.plugins.genesis.android.base)
}
```

**JVM Module:**

```kotlin
plugins {
    alias(libs.plugins.genesis.jvm)
}
```

**Module with OpenAPI:**

```kotlin
plugins {
    alias(libs.plugins.genesis.android.library)
    alias(libs.plugins.genesisopenapi.generator)
}
```

## Setup Steps

1. **Run verification:**
   ```bash
   verify-genesis-plugins.bat
   ```

2. **Cleanup old files:**
   ```bash
   cleanup-old-genesis-plugins.bat
   ```

3. **Clean & rebuild:**
   ```bash
   ./gradlew clean
   cd build-logic && ./gradlew build && cd ..
   ```

4. **Sync Gradle** in Android Studio

5. **Test build:**
   ```bash
   ./gradlew build
   ```

## Files Created/Fixed

### ✅ Convention Plugin Files

- `build-logic/src/main/kotlin/genesisapplication.kt`
- `build-logic/src/main/kotlin/genesislibrary.kt`
- `build-logic/src/main/kotlin/genesisbase.kt`
- `build-logic/src/main/kotlin/genesisjvm.kt`
- `build-logic/src/main/kotlin/genesisopenapigenerator.kt`

### ✅ Property Files

- `genesis.android.application.properties`
- `genesis.android.library.properties`
- `genesis.android.base.properties`
- `genesis.jvm.properties`
- `genesisopenapi.generator.properties`

### ✅ Configuration

- `build-logic/build.gradle.kts` (updated with proper plugin registration)

## Features by Plugin

### `genesis.android.application`

- ✅ Android Application setup
- ✅ Hilt DI
- ✅ KSP processing
- ✅ Compose Compiler
- ✅ Firebase/Google Services
- ✅ ProGuard optimization
- ✅ Debug/Release variants
- ✅ Core library desugaring
- ✅ KSP cache cleanup

### `genesis.android.library`

- ✅ Android Library setup
- ✅ Hilt DI
- ✅ KSP processing
- ✅ Compose support
- ✅ Java 25/24 support
- ✅ Consumer ProGuard

### `genesis.android.base`

- ✅ Kotlin Serialization
- ✅ Native support (placeholder)

### `genesis.jvm`

- ✅ Pure Kotlin JVM
- ✅ Java 25/24 support
- ✅ Kotlin toolchain
- ✅ Context receivers

### `genesisopenapi.generator`

- ✅ OpenAPI Generator 7.16.0
- ✅ Retrofit2 generation
- ✅ kotlinx.serialization
- ✅ Hilt integration
- ✅ Coroutines support
- ✅ Clean task

## Versions Preserved

All versions kept as-is per your request:

- AGP: 9.0.0-alpha10
- Kotlin: 2.0.0
- Hilt: 2.57.2
- OpenAPI Generator: 7.16.0

---
**Status**: All fixed and ready to roll! 🎉
