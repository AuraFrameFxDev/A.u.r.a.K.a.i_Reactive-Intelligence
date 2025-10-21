package dev.aurakai.auraframefx.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Utility functions for handling theme-related operations.
 */
object ThemeUtils {
    /**
     * Check if the system is currently in dark theme mode.
     */
    @Composable
    fun isDarkTheme(): Boolean {
        val context = LocalContext.current
        return when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            Configuration.UI_MODE_NIGHT_NO -> false
            else -> isSystemInDarkTheme()
        }
    }

    /**
     * Get the appropriate surface color based on the current theme.
     */
    @Composable
    fun getSurfaceColor(): Color =
        if (isDarkTheme()) {
            MaterialTheme.colorScheme.surface
        } else {
            MaterialTheme.colorScheme.surface
        }

    /**
     * Get the appropriate onSurface color based on the current theme.
     */
    @Composable
    fun getOnSurfaceColor(): Color =
        if (isDarkTheme()) {
            MaterialTheme.colorScheme.onSurface
        } else {
            MaterialTheme.colorScheme.onSurface
        }

    /**
     * Get the appropriate primary color based on the current theme.
     */
    @Composable
    fun getPrimaryColor(): Color = MaterialTheme.colorScheme.primary

    /**
     * Get the appropriate secondary color based on the current theme.
     */
    @Composable
    fun getSecondaryColor(): Color = MaterialTheme.colorScheme.secondary

    /**
     * Get the appropriate error color based on the current theme.
     */
    @Composable
    fun getErrorColor(): Color = MaterialTheme.colorScheme.error

    /**
     * Get the appropriate background color based on the current theme.
     */
    @Composable
    fun getBackgroundColor(): Color =
        if (isDarkTheme()) {
            MaterialTheme.colorScheme.background
        } else {
            MaterialTheme.colorScheme.background
        }

    /**
     * Get the appropriate color for text on primary surface.
     */
    @Composable
    fun getOnPrimaryColor(): Color = MaterialTheme.colorScheme.onPrimary

    /**
     * Get the appropriate color for text on secondary surface.
     */
    @Composable
    fun getOnSecondaryColor(): Color = MaterialTheme.colorScheme.onSecondary

    /**
     * Get the appropriate color for text on error surface.
     */
    @Composable
    fun getOnErrorColor(): Color = MaterialTheme.colorScheme.onError

    /**
     * Get the appropriate color for text on background.
     */
    @Composable
    fun getOnBackgroundColor(): Color = MaterialTheme.colorScheme.onBackground

    /**
     * Get the appropriate color for surface variant.
     */
    @Composable
    fun getSurfaceVariantColor(): Color = MaterialTheme.colorScheme.surfaceVariant

    /**
     * Get the appropriate color for text on surface variant.
     */
    @Composable
    fun getOnSurfaceVariantColor(): Color = MaterialTheme.colorScheme.onSurfaceVariant

    /**
     * Get the appropriate color for outline.
     */
    @Composable
    fun getOutlineColor(): Color = MaterialTheme.colorScheme.outline

    /**
     * Get the appropriate color for outline variant.
     */
    @Composable
    fun getOutlineVariantColor(): Color = MaterialTheme.colorScheme.outlineVariant
}
