package org.cometchat.cardrender.compose.ui.navigation

import android.util.Base64
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.cometchat.cardrender.compose.ui.screens.HomeScreen
import org.cometchat.cardrender.compose.ui.screens.PreviewScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val history = remember { mutableStateListOf<String>() }

    NavHost(
        navController = navController,
        startDestination = "home",
        enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300))
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300))
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300))
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300))
        }
    ) {
        composable("home") {
            HomeScreen(
                history = history,
                onRender = { json ->
                    if (!history.contains(json)) history.add(0, json)
                    val encoded = Base64.encodeToString(json.toByteArray(Charsets.UTF_8), Base64.URL_SAFE or Base64.NO_WRAP)
                    navController.navigate("preview/$encoded")
                },
                onHistoryItemClick = { json ->
                    val encoded = Base64.encodeToString(json.toByteArray(Charsets.UTF_8), Base64.URL_SAFE or Base64.NO_WRAP)
                    navController.navigate("preview/$encoded")
                },
                onDeleteHistoryItem = { json -> history.remove(json) }
            )
        }
        composable("preview/{cardJson}") { backStackEntry ->
            val encoded = backStackEntry.arguments?.getString("cardJson").orEmpty()
            val json = String(Base64.decode(encoded, Base64.URL_SAFE or Base64.NO_WRAP), Charsets.UTF_8)
            PreviewScreen(cardJson = json, onBack = { navController.popBackStack() })
        }
    }
}
