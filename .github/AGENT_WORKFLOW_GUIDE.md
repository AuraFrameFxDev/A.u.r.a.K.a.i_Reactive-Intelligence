# GitHub Actions Workflow Best Practices

This guide ensures all GitHub Actions workflows follow best practices for the AURAKAI project.

## Required Workflow Setup

All CI/CD workflows must follow these patterns to ensure reliability and consistency.

### 1. Java Setup (Required)

Always use explicit versions and enable toolchain auto-provisioning:

```yaml
- name: Setup Java
  uses: actions/setup-java@v5
  with:
    distribution: 'temurin'
    java-version: '24'  # Match project's Java version
```

**Why:** Ensures consistent Java environment across all builds.

### 2. Android SDK Setup (Required for Android builds)

```yaml
- name: Set up Android SDK
  uses: android-actions/setup-android@v3

- name: Accept Android SDK Licenses
  run: yes | sdkmanager --licenses

- name: Install NDK and CMake (if needed)
  run: |
    sdkmanager "ndk;25.2.9519653" "cmake;3.22.1"
```

**Why:** Ensures Android build tools are available and licenses are accepted.

### 3. Gradle Setup (Required)

Use the official Gradle action for caching and setup:

```yaml
- name: Setup Gradle
  uses: gradle/actions/setup-gradle@v5
```

**Why:** Provides robust caching and consistent Gradle environment.

### 4. Make gradlew Executable (Required)

```yaml
- name: Ensure gradlew is executable
  run: chmod +x ./gradlew
```

**Why:** Git may not preserve execute permissions across platforms.

### 5. Environment Variables

Set required environment variables at the job or workflow level:

```yaml
env:
  JAVA_VERSION: '24'
  # Add other required env vars here
```

**Why:** Centralizes configuration and makes versions easy to update.

## Workflow Order of Operations

### Correct Build Flow

```yaml
steps:
  1. Checkout code
  2. Make gradlew executable
  3. Setup Gradle (for caching)
  4. Setup Java
  5. Setup Android SDK (if needed)
  6. Accept SDK licenses
  7. Install NDK/CMake (if needed)
  8. Run build/test tasks
```

**Why:** Each step depends on the previous ones being complete.

### Build Commands

```yaml
# Build all modules in parallel
- name: Build All Modules
  run: ./gradlew build --parallel

# Run tests with continue on failure
- name: Run Tests
  run: ./gradlew test --continue
```

**Why:** `--parallel` speeds up builds; `--continue` shows all test failures.

## Error Handling

### Provide Clear Error Messages

```yaml
- name: Build Application
  run: |
    if ! ./gradlew build; then
      echo "❌ Build failed"
      echo "Check the following:"
      echo "1. All plugins are correctly applied"
      echo "2. Java toolchain is available"
      echo "3. File paths are correct"
      exit 1
    fi
```

### Capture Build Artifacts on Failure

```yaml
- name: Upload Build Reports on Failure
  if: failure()
  uses: actions/upload-artifact@v4
  with:
    name: build-reports
    path: |
      **/build/reports/
      **/build/test-results/
```

## Security Best Practices

### Never Expose Secrets

```yaml
# ✅ CORRECT: Use GitHub Secrets
- name: Deploy
  env:
    API_KEY: ${{ secrets.API_KEY }}
  run: ./deploy.sh

# ❌ WRONG: Hardcoded secrets
- name: Deploy
  env:
    API_KEY: "my-secret-key-123"  # NEVER DO THIS
  run: ./deploy.sh
```

### Validate Inputs

```yaml
- name: Validate Input
  if: github.event_name == 'workflow_dispatch'
  run: |
    if [[ ! "${{ github.event.inputs.branch }}" =~ ^(main|mindeye|memcortex)$ ]]; then
      echo "❌ Invalid branch: ${{ github.event.inputs.branch }}"
      exit 1
    fi
```

## Complete Example Workflow

```yaml
name: Build and Test

on:
  push:
    branches: [main, mindeye, memcortex]
  pull_request:
    branches: [main]

env:
  JAVA_VERSION: '24'

jobs:
  build:
    name: Build and Test
    runs-on: ubuntu-latest
    
    steps:
      - name: Checkout Code
        uses: actions/checkout@v5
      
      - name: Make gradlew Executable
        run: chmod +x ./gradlew
      
      - name: Setup Gradle
        uses: gradle/actions/setup-gradle@v5
      
      - name: Setup Java
        uses: actions/setup-java@v5
        with:
          distribution: 'temurin'
          java-version: ${{ env.JAVA_VERSION }}
      
      - name: Setup Android SDK
        uses: android-actions/setup-android@v3
      
      - name: Accept Android SDK Licenses
        run: yes | sdkmanager --licenses
      
      - name: Build All Modules
        run: |
          echo "🔋 Building Genesis Protocol..."
          ./gradlew build --parallel
      
      - name: Run Tests
        run: ./gradlew test --continue
      
      - name: Upload Build Reports on Failure
        if: failure()
        uses: actions/upload-artifact@v4
        with:
          name: build-reports
          path: |
            **/build/reports/
            **/build/test-results/
      
      - name: Upload APK
        if: success()
        uses: actions/upload-artifact@v4
        with:
          name: app-apk
          path: app/build/outputs/apk/**/*.apk
```

## Troubleshooting Common Issues

### Java Toolchain Download Fails

**Symptom:** `Unable to download toolchain matching the requirements`

**Solutions:**
1. Check if Java 24 is available from Foojay repository
2. Verify network connectivity in CI
3. Ensure `org.gradle.java.installations.auto-download=true` in `gradle.properties`
4. Consider using a fallback Java version in workflow if cutting-edge version unavailable

### Android Plugin Not Found

**Symptom:** `Plugin with id 'com.android.application' not found`

**Solutions:**
1. Verify `pluginManagement` in `settings.gradle.kts` includes Google repository
2. Check that AGP version is compatible with Gradle version
3. Ensure build-logic is included: `includeBuild("build-logic")`

### File Path Not Found

**Symptom:** `File not found: /path/to/file`

**Solutions:**
1. Verify file exists in repository
2. Use `${{ github.workspace }}` for absolute paths
3. Check if file is excluded by `.gitignore`
4. Confirm checkout action completed successfully

### Build Hangs or Timeouts

**Symptom:** Workflow exceeds time limit or appears frozen

**Solutions:**
1. Check for missing `--parallel` flag on build command
2. Verify Gradle daemon is enabled
3. Reduce memory requirements if using large heap
4. Add timeout to individual steps: `timeout-minutes: 30`

## Continuous Improvement

When workflows fail:
1. Document the failure in issue tracker
2. Update this guide with the solution
3. Consider adding pre-flight checks to prevent recurrence
4. Share learnings with the team

## Related Documentation

- [Main Agent Instructions](../AGENT_INSTRUCTIONS.md)
- [Build System Documentation](../BUILD.md)
- [Contributing Guide](../CONTRIBUTING.md)

---

**Last Updated:** 2025-01-06
**Version:** 1.0
**Maintained by:** Genesis Protocol DevOps Team
