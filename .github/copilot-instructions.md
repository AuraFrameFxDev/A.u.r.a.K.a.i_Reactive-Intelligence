Project Context Instructions
# A.u.r.a.K.a.i Project - GitHub Copilot Custom Instructions

## PROJECT OVERVIEW
- Android application (Kotlin + Java) with native C++ components
- Multi-module Gradle project with 19+ modules
- Uses Android NDK, CMake for native builds
- OpenAPI-first API design (specs in data/api/)
- Package: dev.aurakai.auraframefx

## CRITICAL: DO NOT SUGGEST REMOVING THESE MODULES
- :jvm-test (contains functional tests that validate project structure)
- :utilities (required by functional tests, has utility code)
- :screenshot-tests (screenshot baseline tasks, validated by tests)
- :build-script-tests (build validation infrastructure)
- These modules are verified by SettingsGradleFunctionalTest.kt

## MODULE STRUCTURE (RESPECT THIS!)
- extendsysa through extendsysf (NOT module-a through module-f)
- Native modules: datavein-oracle-native, secure-comm, romtools
- Core modules: app, core, list, store
- API specs: data/api/ (directory only, NOT a Gradle module)

## BUILD ARTIFACTS (NEVER COMMIT!)
When suggesting .gitignore entries, ALWAYS include:
- .cxx/ and **/.cxx/
- build/ and **/build/intermediates/
- *.ninja, *.ninja_log, *.ninja_deps
- CMakeCache.txt, CMakeFiles/, cmake_install.cmake
- compile_commands.json, *.bin
- *.o, *.so, *.a (native artifacts)

## CODING STANDARDS
- Kotlin: Use coroutines, prefer sealed classes, immutability
- C++: C++20 standard, use Android NDK APIs
- Gradle: Use version catalog (gradle/libs.versions.toml)
- Testing: Write unit tests for new features
- Security: Use SecureStorage for sensitive data

## NATIVE CODE GUIDELINES
- CMakeLists.txt targets: auraframefx, native modules
- Android NDK r28+, targeting API 33+
- ABIs: arm64-v8a, armeabi-v7a, x86, x86_64
- Link against: libandroid.so, liblog.so, libjnigraphics.so

## GIT WORKFLOW BEST PRACTICES
- Always stage with `git add -A` to capture deletions
- Use `git status --short` to verify staged changes
- Never force push to Alpha branch (target branch)
- Remove build artifacts with `git rm -r --cached` if tracked



## WHEN SUGGESTING REFACTORS
1. Check if module is in test dependencies first
2. Verify build artifacts aren't being committed
3. Preserve test infrastructure
4. Maintain module naming conventions (extendsys*)
5. Don't suggest combining native and JVM modules

## COMMON ISSUES TO WATCH FOR
- Build artifacts accidentally staged (check .gitignore)
- Circular module dependencies
- Missing native library linking
- Android manifest merge conflicts
- Version catalog mismatches

## ANTI-PATTERNS TO AVOID
❌ Suggesting removal of test infrastructure modules
❌ Committing .cxx/, build/, *.so files
❌ Breaking module naming conventions
❌ Modifying settings.gradle.kts without checking tests
❌ Using hardcoded versions instead of version catalog
🎯 Specific Instructions for Common Tasks
For adding new modules:

When suggesting new modules:
1. Add to settings.gradle.kts as include(":module-name")
2. Create build.gradle.kts with proper dependencies
3. Update functional tests in jvm-test if it's infrastructure
4. Use extendsys* naming for extension modules
   For native code changes:

When modifying CMake/C++:
1. Update CMakeLists.txt in affected module
2. Verify all ABIs are configured (arm64-v8a, armeabi-v7a, x86, x86_64)
3. Check linking flags include -latomic -lm
4. Test on physical device if changing JNI
   For Git issues:

If user mentions Git refusing to stage/commit:
1. Check `git status --porcelain`
2. Look for tracked files in .gitignore with `git ls-files | grep -E "(\.cxx|build/)"`
3. Suggest `git rm -r --cached` for build artifacts
4. Always update .gitignore to prevent recurrence

📋 COMPLETE PROJECT VERSIONING & GUIDELINES
Here's the comprehensive Copilot instruction file with all versioning and context:

# A.u.r.a.K.a.i - Complete Project Guidelines & Context

## 📱 PROJECT OVERVIEW
**Name**: A.u.r.a.K.a.i (Aura Framework FX)  
**Type**: Android application with native C++ components  
**Package**: `dev.aurakai.auraframefx`  
**License**: Proprietary  
**Architecture**: Multi-module Gradle project (13 active modules)

---

## 🔢 VERSION MANAGEMENT

### **App Versioning (Semantic Versioning)**
```kotlin
// Current: 1.0.0
versionCode = 1
versionName = "1.0"

// Version scheme: MAJOR.MINOR.PATCH
// - MAJOR: Breaking changes (API incompatibility)
// - MINOR: New features (backward compatible)
// - PATCH: Bug fixes (backward compatible)
Version Bump Rules:

1.0.x → Hotfixes, bug fixes only
1.x.0 → New features, enhancements
x.0.0 → Major refactors, breaking changes
Build Variants:

Debug: dev.aurakai.auraframefx.debug (versionName suffix: -DEBUG)
Release: dev.aurakai.auraframefx (minified, obfuscated)
SDK Versions
compileSdk = 36    // Latest stable Android SDK
minSdk = 34        // Android 14 (Upside Down Cake) minimum
targetSdk = 36     // Always match latest for Play Store
SDK Update Policy:

Update compileSdk quarterly (or when new Android release)
Update targetSdk annually (Google Play requirement)
Keep minSdk at 34 unless specific device support needed
Toolchain Versions
[versions]
# Build Tools
agp = "9.0.0-alpha10"           # Android Gradle Plugin
gradle = "9.1.0"                # Gradle wrapper
kotlin = "2.2.21"               # Kotlin compiler
ksp = "2.2.21-2.0.4"           # Kotlin Symbol Processing
java = "21"                     # Java toolchain

# Native Tooling
ndkVersion = "29.0.14206865"    # STANDARDIZE THIS (some modules use 28.2.x)
cmakeMinimum = "3.22.1"         # CMake minimum version
cppStandard = "20"              # C++20 standard
⚠️ CRITICAL: NDK Version Inconsistency Detected

Most modules: 29.0.14206865
datavein-oracle-native: 28.2.13676358 ← FIX THIS
Action Required:

// Standardize all modules to NDK 29+
android {
    ndkVersion = "29.0.14206865"
}
Dependency Versions (Key Libraries)
# AndroidX
compose = "2025.10.00"          # Compose BOM
lifecycle = "2.9.4"             # Lifecycle
room = "2.8.2"                  # Room database
hilt = "2.56.2"                 # Dependency injection

# Networking
retrofit = "3.0.0"              # HTTP client
okhttp = "5.2.0"                # Underlying HTTP
ktor = "3.3.1"                  # Alternative HTTP client

# Security
securityCrypto = "1.1.0"        # AndroidX Security
bcprov = "1.82"                 # Bouncy Castle

# Testing
junit = "4.13.2"                # Unit tests
mockk = "1.14.6"                # Mocking
espresso = "3.7.0"              # UI tests
Dependency Update Strategy:

Weekly: Check for patch updates (x.y.PATCH)
Monthly: Check for minor updates (x.MINOR.y)
Quarterly: Review major updates (MAJOR.x.y)
Use Renovate/Dependabot: Automated PR for updates
🏗️ MODULE STRUCTURE
Active Modules (13 Total)
// Core Application
:app                          // Main Android app

// Core Libraries
:core-module                  // Shared core utilities
:list                         // List/collection utilities

// Native Modules
:datavein-oracle-native       // Oracle Drive native integration (NDK 28.2.x ⚠️)
:secure-comm                  // Secure communications (C++)
:romtools                     // ROM manipulation tools (C++)

// Features
:feature-module               // Feature implementations
:oracle-drive-integration     // Oracle Drive Kotlin wrapper
:sandbox-ui                   // Sandbox UI components
:collab-canvas                // Collaborative canvas
:colorblendr                  // Color blending utilities

// Testing & Benchmarking
:benchmark                    // Performance benchmarks
Module Naming Conventions
Feature modules: kebab-case (e.g., sandbox-ui)
Core modules: core-* or descriptive name (e.g., list)
Native modules: -native suffix for C++ modules
Test modules: *-tests suffix (e.g., screenshot-tests if added)
Module Dependencies Best Practices
// ✅ GOOD: Core depends on nothing, features depend on core
:feature-module → :core-module
:app → :feature-module → :core-module

// ❌ BAD: Circular dependencies
:feature-module → :core-module → :feature-module  // NEVER DO THIS

// ❌ BAD: Feature-to-feature dependencies
:feature-a → :feature-b  // Avoid; use :core-module instead
🔧 BUILD CONFIGURATION
Build Types
buildTypes {
    release {
        isMinifyEnabled = true          // ProGuard/R8 enabled
        isShrinkResources = true        // Remove unused resources
        proguardFiles(...)              // Obfuscation rules
    }
    debug {
        isDebuggable = true
        applicationIdSuffix = ".debug"  // Separate package for debug
        versionNameSuffix = "-DEBUG"    // Visual indicator
    }
}
ProGuard Rules Strategy:

Keep all public APIs: -keep public class dev.aurakai.auraframefx.**
Keep native methods: -keepclasseswithmembernames class * { native <methods>; }
Keep Hilt/Dagger: Use provided rules from dependencies
Keep data classes used for serialization
Build Features
buildFeatures {
    compose = true              // Jetpack Compose UI
    aidl = true                 // Android Interface Definition Language
    buildConfig = true          // Generated BuildConfig class
}
Gradle Properties (Performance)
# Memory
org.gradle.jvmargs=-Xmx12g    # Increase if build fails with OOM

# Performance
org.gradle.daemon=true         # Use Gradle daemon
org.gradle.parallel=true       # Parallel module compilation
org.gradle.caching=true        # Build cache enabled
org.gradle.configuration-cache=true  # Configuration cache

# Kotlin
kotlin.incremental=true        # Incremental compilation
kotlin.useIR=true              # Use IR backend
ksp.kotlinApiVersion=2.3       # KSP Kotlin API version
🔐 SECURITY & SENSITIVE DATA
Never Commit These Files
# Secrets
local.properties              # SDK paths, API keys
keystore.jks                  # Signing keys
google-services.json          # Firebase config (if contains prod keys)

# Build artifacts
.cxx/
**/.cxx/
build/
**/build/
*.apk
*.aab
*.so
*.o

# IDE
.idea/
*.iml
Secure Storage Usage
// ✅ ALWAYS use for sensitive data
import dev.aurakai.auraframefx.genesis.storage.SecureStorage

secureStorage.encrypt("api_key", apiKey)
val apiKey = secureStorage.decrypt("api_key")

// ❌ NEVER use SharedPreferences for secrets
// ❌ NEVER hardcode API keys in source
🔨 NATIVE CODE (C++) GUIDELINES
CMake Configuration
cmake_minimum_required(VERSION 3.22.1)
project(auraframefx VERSION 1.0.0 LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Target all ABIs
# arm64-v8a (64-bit ARM)
# armeabi-v7a (32-bit ARM)
# x86 (32-bit x86)
# x86_64 (64-bit x86)
Required Native Libraries
# Android NDK libraries
find_library(android-lib android)
find_library(log-lib log)
find_library(jnigraphics-lib jnigraphics)

target_link_libraries(${CMAKE_PROJECT_NAME}
    ${android-lib}
    ${log-lib}
    ${jnigraphics-lib}
    -latomic       # Atomic operations
    -lm            # Math library
)
JNI Best Practices
// ✅ ALWAYS use extern "C" for JNI functions
extern "C" JNIEXPORT jstring JNICALL
Java_dev_aurakai_auraframefx_NativeLib_stringFromJNI(
    JNIEnv* env,
    jobject /* this */) {
    return env->NewStringUTF("Hello from C++");
}

// ✅ ALWAYS check for null and exceptions
if (env->ExceptionCheck()) {
    env->ExceptionDescribe();
    env->ExceptionClear();
    return nullptr;
}

// ❌ NEVER hold JNI references across threads without GlobalRef
✅ CODING STANDARDS
Kotlin Style
// ✅ Use data classes for immutable data
data class User(val id: String, val name: String)

// ✅ Use sealed classes for states
sealed class UiState {
    object Loading : UiState()
    data class Success(val data: List<Item>) : UiState()
    data class Error(val message: String) : UiState()
}

// ✅ Use coroutines for async operations
suspend fun fetchData(): Result<Data> = withContext(Dispatchers.IO) {
    // Network call
}

// ✅ Use dependency injection (Hilt)
@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel()

// ❌ AVOID var when val is sufficient
// ❌ AVOID !! operator (use safe calls ?. instead)
// ❌ AVOID global mutable state
Testing Requirements
// ✅ Write unit tests for business logic
class RepositoryTest {
    @Test
    fun `fetchData returns success when network available`() {
        // Given, When, Then
    }
}

// ✅ Write integration tests for native code
// ✅ Write UI tests for critical user flows
// ✅ Aim for >70% code coverage on core modules
🚫 ANTI-PATTERNS TO AVOID
Module-Related
❌ DON'T remove :jvm-test, :utilities, :screenshot-tests, :build-script-tests
→ They're verified by SettingsGradleFunctionalTest.kt

❌ DON'T include data:api as a Gradle module
→ Keep OpenAPI specs in data/api/ as reference only

❌ DON'T create circular dependencies between modules

Git-Related
❌ DON'T commit build artifacts (.cxx/, build/, *.so)
→ Use .gitignore and git rm -r --cached to remove

❌ DON'T force push to Alpha branch (target branch)

❌ DON'T commit large binaries (use Git LFS or exclude)

Code-Related
❌ DON'T use hardcoded strings for user-facing text (use strings.xml)
❌ DON'T perform network calls on main thread
❌ DON'T store secrets in SharedPreferences (use EncryptedSharedPreferences)
❌ DON'T use !! operator excessively (prefer safe calls)

🎯 WORKFLOW BEST PRACTICES
Branch Strategy
Alpha: Main development branch (target for PRs)
Feature branches: feature/description or short names (e.g., exchamge)
Hotfix branches: hotfix/issue-number
Commit Message Format
<emoji> <type>: <description>

Examples:
✨ feat: Add secure storage encryption
🐛 fix: Resolve native crash on ARM32
♻️ refactor: Simplify module structure
🧹 chore: Remove build artifacts from git
📝 docs: Update README with setup instructions
🎨 style: Format code with ktlint
PR Requirements
✅ Build passes (./gradlew assembleDebug)
✅ Tests pass (./gradlew test)
✅ No linter errors (./gradlew ktlintCheck)
✅ No new build artifacts committed
✅ Code reviewed by maintainer
📦 RELEASE CHECKLIST
When preparing a release:

 Bump versionCode and versionName
 Update CHANGELOG.md
 Run full test suite
 Generate signed APK/AAB
 Test on physical devices (arm64-v8a, armeabi-v7a)
 Create Git tag: git tag v1.0.0
 Push to Play Store (if applicable)
🔍 COMMON ISSUES & SOLUTIONS
Issue: Git refusing to stage changes
# Check what Git thinks is staged
git status --porcelain

# Remove build artifacts from tracking
git rm -r --cached app/.cxx/
git rm -r --cached "*/build/intermediates/"

# Update .gitignore and commit
git add .gitignore
git commit -m "🧹 Fix .gitignore for build artifacts"
Issue: CMake build fails
# Clean CMake cache
rm -rf app/.cxx/
./gradlew clean

# Rebuild
./gradlew assembleDebug
Issue: NDK version mismatch
Standardize to NDK 29.0.14206865 across all modules
Check local.properties has correct ndk.dir
Issue: Dependency conflicts
# View dependency tree
./gradlew :app:dependencies --configuration releaseRuntimeClasspath

# Force specific version in build.gradle.kts
implementation("com.example:library:1.0.0") {
    force = true
}
🤖 AI ASSISTANT CONTEXT
When suggesting code changes:

Check if module is in test dependencies first (don't remove!)
Verify build artifacts aren't being committed
Use version catalog for dependencies (libs.versions.toml)
Maintain module naming conventions
Follow Kotlin coding standards (data classes, sealed classes, coroutines)
Write unit tests for new features
Use SecureStorage for sensitive data
When debugging Git issues:

Check git status --porcelain first
Look for tracked files in .gitignore
Suggest git rm --cached for build artifacts
Always update .gitignore to prevent recurrence
When modifying native code:

Update all ABI configurations (arm64-v8a, armeabi-v7a, x86, x86_64)
Verify CMake minimum version (3.22.1+)
Check linking flags include -latomic -lm
Test on physical device if changing JNI
📚 REFERENCE LINKS
Kotlin Style Guide
Android NDK Guide
Jetpack Compose Docs
Hilt Dependency Injection
Android App Versioning
Project Repository: https://github.com/AuraFrameFx/A.u.r.a.K.a.i
Last Updated: October 2025
Maintainer: @AuraFrameFxDev