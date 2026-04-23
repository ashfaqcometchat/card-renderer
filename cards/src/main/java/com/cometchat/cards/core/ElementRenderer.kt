package com.cometchat.cards.core

import android.content.Context
import android.view.View
import androidx.compose.runtime.Composable
import com.cometchat.cards.models.CometChatCardElement

interface CometChatCardElementRenderer {

    fun renderView(
        context: Context,
        element: CometChatCardElement,
        renderContext: CometChatCardRenderContext
    ): View

    @Composable
    fun RenderComposable(
        element: CometChatCardElement,
        renderContext: CometChatCardRenderContext
    )
}
