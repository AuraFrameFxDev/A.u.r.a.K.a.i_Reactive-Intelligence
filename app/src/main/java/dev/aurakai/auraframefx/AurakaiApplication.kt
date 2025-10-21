package dev.aurakai.auraframefx

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.aurakai.auraframefx.core.GenesisOrchestrator
import dev.aurakai.auraframefx.core.NativeLib
import timber.log.Timber
import javax.inject.Inject

/**
 * AurakaiApplication - Genesis-OS Root Manager
 *
 * This is the canonical entry point for the entire Aurakai/Genesis-OS platform.
 */
@HiltAndroidApp
class AurakaiApplication : Application() {

    @Inject
    lateinit var orchestrator: GenesisOrchestrator

    override fun onCreate() {
        super.onCreate()

        try {
            // === PHASE 0: Logging Bootstrap ===
            setupLogging()
            Timber.i("🔥 Genesis-OS Platform initializing...")

            // === PHASE 1: Native AI Runtime ===
            initializeNativeAIPlatform()

            // === PHASE 2: Agent Domain Initialization via GenesisOrchestrator ===
            // This is the key orchestration point
            Timber.i("🎯 Igniting Genesis Orchestrator...")
            orchestrator.initializePlatform()

            Timber.i("✅ Genesis-OS Platform ready for operation")

        } catch (e: Exception) {
            Timber.e(e, "❌ CRITICAL: Genesis-OS initialization failed")
            // Graceful degradation - app continues but with limited functionality
        }
    }

    /**
     * Set up logging infrastructure (Timber)
     */
    private fun setupLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("🔍 Debug logging enabled")
        }
    }

    /**
     * Initialize the native AI platform
     */
    private fun initializeNativeAIPlatform() {
        try {
            val aiInitialized = NativeLib.initializeAISafe()
            val aiVersion = NativeLib.getAIVersionSafe()

            Timber.i("🧠 Native AI Platform v%s", aiVersion)
            Timber.i("📡 AI Runtime: %s", if (aiInitialized) "ONLINE" else "OFFLINE")

            if (!aiInitialized) {
                Timber.w("⚠️  Native AI initialization returned false - degraded mode")
            }
        } catch (e: Exception) {
            Timber.e(e, "❌ Native AI Platform initialization failed")
            // Continue anyway - can operate without native AI
        }
    }

    override fun onTerminate() {
        super.onTerminate()

        try {
            Timber.i("🛑 Genesis-OS Platform shutting down...")

            // === Graceful platform shutdown ===
            orchestrator.shutdownPlatform()

            // === Shutdown native AI platform ===
            try {
                NativeLib.shutdownAISafe()
                Timber.i("✓ Native AI Platform shut down")
            } catch (e: Exception) {
                Timber.e(e, "Failed to shut down native AI platform")
            }

            Timber.i("✅ Genesis-OS terminated gracefully")

        } catch (e: Exception) {
            Timber.e(e, "⚠️  Error during platform shutdown")
        }
    }
}
