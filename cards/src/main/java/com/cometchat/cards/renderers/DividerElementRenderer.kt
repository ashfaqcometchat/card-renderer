package com.cometchat.cards.renderers

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardDividerElement
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.theme.CometChatCardThemeResolver

class DividerElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardDividerElement
        val mode = renderContext.effectiveThemeMode
        val thickness = el.thickness ?: 1
        val margin = el.margin ?: 0
        val color = CometChatCardThemeResolver.resolveColor(el.color, mode, renderContext.resolvedTheme.dividerColor)
        val density = context.resources.displayMetrics.density

        return View(context).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (thickness * density).toInt()).apply {
                topMargin = (margin * density).toInt()
                bottomMargin = (margin * density).toInt()
            }
            color?.let { runCatching { setBackgroundColor(Color.parseColor(it)) } }
            importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
        }
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardDividerElement
        val mode = renderContext.effectiveThemeMode
        val thickness = el.thickness ?: 1
        val margin = el.margin ?: 0
        val color = CometChatCardThemeResolver.resolveColor(el.color, mode, renderContext.resolvedTheme.dividerColor)
        val bgColor = color?.let { runCatching { androidx.compose.ui.graphics.Color(Color.parseColor(it)) }.getOrNull() }
            ?: androidx.compose.ui.graphics.Color.Gray

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(thickness.dp)
                .padding(vertical = margin.dp)
                .background(bgColor)
        )
    }
}
