package org.cometchat.cardrender.compose.ui.navigation

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
import java.net.URLDecoder
import java.net.URLEncoder

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
                    navController.navigate("preview/${URLEncoder.encode(json, "UTF-8")}")
                },
                onHistoryItemClick = { json ->
                    navController.navigate("preview/${URLEncoder.encode(json, "UTF-8")}")
                },
                onDeleteHistoryItem = { json -> history.remove(json) }
            )
        }
        composable("preview/{cardJson}") { backStackEntry ->
            val json = URLDecoder.decode(
                backStackEntry.arguments?.getString("cardJson").orEmpty(), "UTF-8"
            )
            PreviewScreen(cardJson = json, onBack = { navController.popBackStack() })
        }
    }
}
