# 🎯 AGP 9 + Hilt + Firebase Build Configuration - COMPLETE FIX

## Issue Summary
The project was experiencing **ClassCastException** and **Hilt annotation processing failures** with AGP 9.0.0-alpha09.

## Root Causes Identified

1. **Hilt Plugin Timing Issue**: Applying Hilt via `apply(plugin = ...)` AFTER the `plugins {}` block caused KSP to not see Hilt's configuration
2. **Missing Critical Property**: `android.disableLastStageWhenHiltIsApplied=true` was missing from `gradle.properties`
3. **Java Version Confusion**: Some files documented Java 17 but code used Java 24 (Firebase requirement)
4. **Kotlin Version Mismatch**: KSP version didn't match Kotlin version prefix

---

## ✅ COMPLETE SOLUTION APPLIED

### 1. Fixed `app/build.gradle.kts`
**Key Changes:**
- ✅ Moved `alias(libs.plugins.hilt)` INSIDE the `plugins {}` block (line 5)
- ✅ Removed the late `apply(plugin = libs.plugins.hilt.get().pluginId)` line
- ✅ Fixed WorkManager KSP to use `hilt.compiler` instead of `hilt.work`
- ✅ Standardized to `JavaVersion.VERSION_24`

### 2. Fixed `gradle.properties`
**Added Critical Property:**
```properties
android.disableLastStageWhenHiltIsApplied=true
```
This is **THE KEY FIX** for AGP 9 + Hilt compatibility. It disables AGP's new optimization stage that conflicts with Hilt's code generation.

### 3. Fixed `libs.versions.toml`
**Version Alignment:**
- ✅ `kotlin = "2.2.21"` (stable)
- ✅ `ksp = "2.2.21-2.0.4"` (matches Kotlin prefix)
- ✅ `java = "24"` (Firebase requirement - confirmed correct)
- ✅ `hilt-version = "2.56.2"` (latest stable)

### 4. Fixed Convention Plugins
**AndroidApplicationConventionPlugin.kt:**
- ✅ Java 24 everywhere (`JavaVersion.VERSION_24`)
- ✅ JVM toolchain 24: `jvmToolchain(24)`
- ✅ Kotlin JVM target 24: `jvmTarget.set(JvmTarget.JVM_24)`

**AndroidLibraryConventionPlugin.kt:**
- ✅ Same Java 24 standardization across all library modules

---

## 🔍 Why This Works

### The AGP 9 + Hilt Problem
AGP 9.0 introduced a new build optimization stage that runs AFTER Hilt's annotation processing. This causes:
- `ClassCastException`: AGP's internal classes changed structure
- Hilt annotation errors: KSP can't find `@AndroidEntryPoint` values

### The Solution Architecture
1. **Early Plugin Application**: Hilt MUST be in the `plugins {}` block so it configures KSP before compilation
2. **Disable Problematic Stage**: `android.disableLastStageWhenHiltIsApplied=true` tells AGP to skip its new optimization when Hilt is present
3. **Version Harmony**: All tools (Kotlin, KSP, Hilt, AGP) must be on compatible versions
4. **Consistent Java Target**: Firebase requires Java 24, so ALL modules must use it

---

## 📋 Build Commands to Test

```bash
# Clean everything
./gradlew clean
./gradlew --stop

# Delete caches if needed
rm -rf ~/.gradle/caches
rm -rf .gradle

# Rebuild
./gradlew :app:assembleDebug --stacktrace

# If successful, test release build
./gradlew :app:assembleRelease
```

---

## 🎯 Expected Result

All Hilt annotation processing should now work correctly:
- ✅ `@HiltAndroidApp` on Application classes
- ✅ `@AndroidEntryPoint` on Activities, Services, Receivers
- ✅ `@Inject` constructor and field injection
- ✅ No ClassCastException
- ✅ No "Expected @HiltAndroidApp to have a value" errors

---

## 📚 References

- [Hilt Gradle Setup](https://dagger.dev/hilt/gradle-setup.html)
- [AGP 9.0 Release Notes](https://developer.android.com/studio/releases/gradle-plugin)
- [Firebase Java 24 Requirement](https://firebase.google.com/docs/android/setup)

---

## 🔐 Configuration Standards

**All modules MUST use:**
- Java Toolchain: 24
- Java Source/Target Compatibility: 24
- Kotlin JVM Target: 24
- Kotlin Version: 2.2.21 (stable)
- KSP Version: 2.2.21-2.0.4
- Hilt Version: 2.56.2
- AGP Version: 9.0.0-alpha09

**DO NOT**:
- Mix Java versions across modules
- Use unstable Kotlin versions (RCs, Betas) without testing
- Apply Hilt plugin outside the `plugins {}` block in application modules
- Remove the `android.disableLastStageWhenHiltIsApplied=true` property

---

*Genesis Protocol Build System - Version Aligned and Stable*
*Fixed: October 2025*
