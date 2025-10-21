@echo off
echo ========================================
echo Genesis Plugins - Cleanup Old Files
echo ========================================
echo.

cd /d "%~dp0"

echo Removing OLD convention plugin files...
del /F /Q "build-logic\src\main\kotlin\GenesisApplicationPlugin.kt" 2>nul
del /F /Q "build-logic\src\main\kotlin\GenesisLibraryPlugin.kt" 2>nul
del /F /Q "build-logic\src\main\kotlin\GenesisJvmPlugin.kt" 2>nul
del /F /Q "build-logic\src\main\kotlin\GenesisOpenApiPlugin.kt" 2>nul

echo.
echo Removing OLD property files...
del /F /Q "build-logic\src\main\resources\META-INF\gradle-plugins\genesis.application.properties" 2>nul
del /F /Q "build-logic\src\main\resources\META-INF\gradle-plugins\genesis.library.properties" 2>nul
del /F /Q "build-logic\src\main\resources\META-INF\gradle-plugins\genesis.base.properties" 2>nul
del /F /Q "build-logic\src\main\resources\META-INF\gradle-plugins\genesis.openapi.properties" 2>nul
del /F /Q "build-logic\src\main\resources\META-INF\gradle-plugins\genesis.openapi.generator.properties" 2>nul

echo.
echo ========================================
echo Cleanup Complete!
echo ========================================
echo.
echo NEW Files (should remain):
echo   - genesisapplication.kt
echo   - genesislibrary.kt
echo   - genesisbase.kt
echo   - genesisjvm.kt
echo   - genesisopenapigenerator.kt
echo.
echo NEW Property Files (should remain):
echo   - genesis.android.application.properties
echo   - genesis.android.library.properties
echo   - genesis.android.base.properties
echo   - genesis.jvm.properties
echo   - genesisopenapi.generator.properties
echo.
pause
