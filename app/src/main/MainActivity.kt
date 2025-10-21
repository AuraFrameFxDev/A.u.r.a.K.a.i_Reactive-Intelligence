package dev.aurakai.auraframefx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import timber.log.Timber

/**
 * MainActivity - simplified, robust entrypoint for the Compose UI.
 * Placeholder until AurakaiApp composable is properly implemented.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            Timber.d("🧠 Aurakai MainActivity launching...")

            setContent {
                MaterialTheme {
                    PlaceholderScreen()
                }
            }

            Timber.i("🌟 Aurakai Interface Active")

        } catch (t: Throwable) {
            Timber.e(t, "MainActivity initialization error")
            finish()
        }
    }

}

@Composable
fun PlaceholderScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Aurakai Loading...",
            color = Color.Green,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}