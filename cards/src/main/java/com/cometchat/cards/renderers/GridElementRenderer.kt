package com.cometchat.cards.renderers

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.models.CometChatCardGridElement
import com.cometchat.cards.models.type
import com.cometchat.cards.theme.CometChatCardThemeResolver

class GridElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardGridElement
        val mode = renderContext.effectiveThemeMode
        val density = context.resources.displayMetrics.density
        val columns = (el.columns ?: 2).coerceIn(2, 4)
        val gap = el.gap ?: 8

        if (renderContext.depth >= CometChatCardRenderContext.MAX_DEPTH) {
            renderContext.logger.warning("Max nesting depth exceeded, id=${el.id}")
            return View(context)
        }

        val grid = GridLayout(context).apply {
            columnCount = columns
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            applyLayoutBackground(this, el.backgroundColor, el.borderRadius, el.borderColor, el.borderWidth, mode, density)
            applyPadding(this, el.padding)
        }

        for (child in el.items) {
            val renderer = renderContext.registry.getRenderer(child.type)
            if (renderer != null) {
                try {
                    val childView = renderer.renderView(context, child, renderContext.withDepth(renderContext.depth + 1))
                    val lp = GridLayout.LayoutParams().apply {
                        width = 0
                        columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                        setMargins((gap / 2 * density).toInt(), (gap / 2 * density).toInt(), (gap / 2 * density).toInt(), (gap / 2 * density).toInt())
                    }
                    childView.layoutParams = lp
                    grid.addView(childView)
                } catch (e: Exception) {
                    renderContext.logger.error("Renderer exception for ${child.type}: ${e.message}")
                }
            } else {
                renderContext.logger.warning("Unknown element type: ${child.type}, id: ${child.id}")
            }
        }

        return grid
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardGridElement
        val mode = renderContext.effectiveThemeMode
        val columns = (el.columns ?: 2).coerceIn(2, 4)
        val gap = el.gap ?: 8
        val borderRadius = el.borderRadius ?: 0
        val shape = RoundedCornerShape(borderRadius.dp)

        if (renderContext.depth >= CometChatCardRenderContext.MAX_DEPTH) {
            renderContext.logger.warning("Max nesting depth exceeded, id=${el.id}")
            return
        }

        var modifier = composePadding(el.padding).fillMaxWidth()
        if (borderRadius > 0) modifier = modifier.clip(shape)
        CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode)?.let { modifier = modifier.background(parseComposeColor(it), shape) }
        val borderColor = CometChatCardThemeResolver.resolveColor(el.borderColor, mode)
        if (borderColor != null && el.borderWidth != null) modifier = modifier.border(el.borderWidth.dp, parseComposeColor(borderColor), shape)

        // Use Column+Row instead of LazyVerticalGrid to avoid measurement issues in scrollable parents
        val rows = el.items.chunked(columns)
        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(gap.dp)) {
            for (row in rows) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(gap.dp)) {
                    for (child in row) {
                        Box(modifier = Modifier.weight(1f)) {
                            val renderer = renderContext.registry.getRenderer(child.type)
                            if (renderer != null) {
                                renderer.RenderComposable(child, renderContext.withDepth(renderContext.depth + 1))
                            }
                        }
                    }
                    // Fill remaining cells in last row with empty spacers
                    repeat(columns - row.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
