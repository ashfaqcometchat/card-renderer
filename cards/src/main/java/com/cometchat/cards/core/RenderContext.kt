package com.cometchat.cards.core

import com.cometchat.cards.actions.CometChatCardActionCallback
import com.cometchat.cards.models.CometChatCardThemeMode
import com.cometchat.cards.theme.CometChatCardResolvedTheme

data class CometChatCardRenderContext(
    val effectiveThemeMode: CometChatCardThemeMode,
    val resolvedTheme: CometChatCardResolvedTheme,
    val onAction: CometChatCardActionCallback?,
    val cardJson: String,
    val depth: Int,
    val registry: CometChatCardElementRegistry,
    val loadingStateManager: CometChatCardLoadingStateManager,
    val logger: CometChatCardLogger = CometChatCardLogger
) {
    companion object {
        const val MAX_DEPTH = 5
    }

    fun withDepth(newDepth: Int): CometChatCardRenderContext {
        return copy(depth = newDepth)
    }
}
