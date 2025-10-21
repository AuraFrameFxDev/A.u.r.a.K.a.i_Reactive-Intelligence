@echo off
echo ========================================
echo Genesis Plugins - Verification Script
echo ========================================
echo.

cd /d "%~dp0"

echo Checking NEW Genesis Convention Plugin Files...
echo.

set "ALL_GOOD=1"

REM Check new plugin files
if exist "build-logic\src\main\kotlin\genesisapplication.kt" (
    echo [OK] genesisapplication.kt
) else (
    echo [MISSING] genesisapplication.kt
    set "ALL_GOOD=0"
)

if exist "build-logic\src\main\kotlin\genesislibrary.kt" (
    echo [OK] genesislibrary.kt
) else (
    echo [MISSING] genesislibrary.kt
    set "ALL_GOOD=0"
)

if exist "build-logic\src\main\kotlin\genesisbase.kt" (
    echo [OK] genesisbase.kt
) else (
    echo [MISSING] genesisbase.kt
    set "ALL_GOOD=0"
)

if exist "build-logic\src\main\kotlin\genesisjvm.kt" (
    echo [OK] genesisjvm.kt
) else (
    echo [MISSING] genesisjvm.kt
    set "ALL_GOOD=0"
)

if exist "build-logic\src\main\kotlin\genesisopenapigenerator.kt" (
    echo [OK] genesisopenapigenerator.kt
) else (
    echo [MISSING] genesisopenapigenerator.kt
    set "ALL_GOOD=0"
)

echo.
echo Checking NEW Property Files...
echo.

if exist "build-logic\src\main\resources\META-INF\gradle-plugins\genesis.android.application.properties" (
    echo [OK] genesis.android.application.properties
) else (
    echo [MISSING] genesis.android.application.properties
    set "ALL_GOOD=0"
)

if exist "build-logic\src\main\resources\META-INF\gradle-plugins\genesis.android.library.properties" (
    echo [OK] genesis.android.library.properties
) else (
    echo [MISSING] genesis.android.library.properties
    set "ALL_GOOD=0"
)

if exist "build-logic\src\main\resources\META-INF\gradle-plugins\genesis.android.base.properties" (
    echo [OK] genesis.android.base.properties
) else (
    echo [MISSING] genesis.android.base.properties
    set "ALL_GOOD=0"
)

if exist "build-logic\src\main\resources\META-INF\gradle-plugins\genesis.jvm.properties" (
    echo [OK] genesis.jvm.properties
) else (
    echo [MISSING] genesis.jvm.properties
    set "ALL_GOOD=0"
)

if exist "build-logic\src\main\resources\META-INF\gradle-plugins\genesisopenapi.generator.properties" (
    echo [OK] genesisopenapi.generator.properties
) else (
    echo [MISSING] genesisopenapi.generator.properties
    set "ALL_GOOD=0"
)

echo.
echo Checking for OLD files that should be removed...
echo.

if exist "build-logic\src\main\kotlin\GenesisApplicationPlugin.kt" (
    echo [WARNING] GenesisApplicationPlugin.kt still exists - should be deleted
    set "ALL_GOOD=0"
)

if exist "build-logic\src\main\kotlin\GenesisLibraryPlugin.kt" (
    echo [WARNING] GenesisLibraryPlugin.kt still exists - should be deleted
    set "ALL_GOOD=0"
)

if exist "build-logic\src\main\kotlin\GenesisJvmPlugin.kt" (
    echo [WARNING] GenesisJvmPlugin.kt still exists - should be deleted
    set "ALL_GOOD=0"
)

if exist "build-logic\src\main\kotlin\GenesisOpenApiPlugin.kt" (
    echo [WARNING] GenesisOpenApiPlugin.kt still exists - should be deleted
    set "ALL_GOOD=0"
)

echo.
echo ========================================
if "%ALL_GOOD%"=="1" (
    echo Status: ALL CHECKS PASSED! ✅
    echo.
    echo Your Genesis plugins are ready to use!
    echo Next steps:
    echo   1. Run: cleanup-old-genesis-plugins.bat ^(if not done yet^)
    echo   2. Clean: ./gradlew clean
    echo   3. Build: cd build-logic ^&^& ./gradlew build
    echo   4. Sync: File -^> Sync Project with Gradle Files
) else (
    echo Status: ISSUES FOUND! ⚠️
    echo Please review the warnings above.
)
echo ========================================
echo.
pause
