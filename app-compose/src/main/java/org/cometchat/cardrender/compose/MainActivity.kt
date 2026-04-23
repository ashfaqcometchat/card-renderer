package org.cometchat.cardrender.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.cometchat.cardrender.compose.ui.navigation.AppNavigation
import org.cometchat.cardrender.compose.ui.theme.CardsComposeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CardsComposeDemoTheme {
                AppNavigation()
            }
        }
    }
}
