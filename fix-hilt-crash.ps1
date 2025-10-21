#!/usr/bin/env pwsh
# Fix script for Re:Genesis ClassNotFoundException

Write-Host "🔧 FIXING Re:Genesis Application Crash" -ForegroundColor Cyan
Write-Host "═══════════════════════════════════════════" -ForegroundColor DarkGray

Write-Host "`n✅ Fixed Issues:" -ForegroundColor Green
Write-Host "   1. Removed duplicate @HiltAndroidApp from AuraKaiHiltApplication"
Write-Host "   2. Added android.builtInKotlin=false to gradle.properties"
Write-Host "   3. Added com.android.base plugin to library modules"
Write-Host "   4. Added kotlin jvmToolchain(24) to data:api module"

Write-Host "`n🧹 Cleaning project..." -ForegroundColor Yellow
./gradlew clean
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Clean failed!" -ForegroundColor Red
    exit 1
}

Write-Host "`n🔨 Building debug APK..." -ForegroundColor Yellow
./gradlew assembleDebug
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Build failed!" -ForegroundColor Red
    exit 1
}

Write-Host "`n✅ BUILD SUCCESSFUL!" -ForegroundColor Green
Write-Host "═══════════════════════════════════════════" -ForegroundColor DarkGray
Write-Host "📱 APK Location:" -ForegroundColor Cyan
Write-Host "   app\build\outputs\apk\debug\app-debug.apk" -ForegroundColor White

Write-Host "`n📋 Summary of Changes:" -ForegroundColor Cyan
Write-Host "   • ReGenesisApplication: ✅ @HiltAndroidApp (Primary)" -ForegroundColor Green
Write-Host "   • AuraKaiHiltApplication: ✅ @HiltAndroidApp removed" -ForegroundColor Yellow
Write-Host "   • AuraFrameApplication: ✅ Already disabled" -ForegroundColor Yellow
Write-Host "   • AuraFrameFxApplication: ✅ Already disabled" -ForegroundColor Yellow
Write-Host "   • AGP Version: 9.0.0-alpha09" -ForegroundColor White
Write-Host "   • Kotlin: 2.2.20" -ForegroundColor White
Write-Host "   • KSP: 2.2.20-2.0.3" -ForegroundColor White
Write-Host "   • Java: 24" -ForegroundColor White

Write-Host "`n🚀 Next Steps:" -ForegroundColor Cyan
Write-Host "   1. Install the APK: adb install -r app\build\outputs\apk\debug\app-debug.apk"
Write-Host "   2. Test the app to ensure it launches without crashing"
Write-Host "   3. Check logs: adb logcat | grep 'Re:Genesis'"

Write-Host "`nEnjoy your break! The app should be working when you return 🎉" -ForegroundColor Green
