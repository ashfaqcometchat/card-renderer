package com.cometchat.cards

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.cometchat.cards.actions.CometChatCardActionCallback
import com.cometchat.cards.actions.CometChatCardActionEvent
import com.cometchat.cards.core.*
import com.cometchat.cards.models.*
import com.cometchat.cards.parser.CometChatCardSchemaParser
import com.cometchat.cards.theme.CometChatCardThemeOverride
import com.cometchat.cards.theme.CometChatCardThemeResolver

@Composable
fun CometChatCardComposable(
    cardJson: String,
    themeMode: CometChatCardThemeMode = CometChatCardThemeMode.AUTO,
    onAction: ((CometChatCardActionEvent) -> Unit)? = null,
    themeOverride: CometChatCardThemeOverride? = null,
    logLevel: CometChatCardLogLevel = CometChatCardLogLevel.WARNING,
    onContainerStyle: ((CometChatCardContainerStyle) -> Unit)? = null
) {
    CometChatCardLogger.logLevel = logLevel

    val parseResult = remember(cardJson) {
        CometChatCardSchemaParser.parse(cardJson)
    }

    if (parseResult.isFailure) {
        CometChatCardLogger.error("Failed to parse card JSON: ${parseResult.exceptionOrNull()?.message}")
        FallbackText(null)
        return
    }

    val schema = parseResult.getOrThrow()

    LaunchedEffect(schema.style) {
        schema.style?.let { onContainerStyle?.invoke(it) }
    }

    val isSystemDark = isSystemInDarkTheme()
    val effectiveMode = remember(themeMode, isSystemDark) {
        CometChatCardThemeResolver.resolveEffectiveMode(themeMode, isSystemDark)
    }

    val resolvedTheme = remember(themeOverride) {
        CometChatCardThemeResolver.resolveTheme(themeOverride)
    }

    val registry = remember { CometChatCardView.createDefaultRegistry() }
    val loadingStateManager = remember { CometChatCardLoadingStateManager() }

    val actionCallback = remember(onAction) {
        onAction?.let { handler ->
            CometChatCardActionCallback { event -> handler(event) }
        }
    }

    val renderContext = remember(effectiveMode, resolvedTheme, actionCallback, cardJson) {
        CometChatCardRenderContext(
            effectiveThemeMode = effectiveMode,
            resolvedTheme = resolvedTheme,
            onAction = actionCallback,
            cardJson = cardJson,
            depth = 0,
            registry = registry,
            loadingStateManager = loadingStateManager
        )
    }

    if (schema.body.isEmpty()) return

    Column(modifier = Modifier.fillMaxWidth()) {
        for (element in schema.body) {
            val renderer = registry.getRenderer(element.type)
            if (renderer != null) {
                renderer.RenderComposable(element, renderContext)
            } else {
                CometChatCardLogger.warning("Skipping unknown element type: ${element.type}, id: ${element.id}")
            }
        }
    }
}

@Composable
private fun FallbackText(text: String?) {
    val displayText = text?.takeIf { it.isNotBlank() } ?: "Unable to display this message"
    Text(
        text = displayText,
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}
