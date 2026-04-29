package com.cometchat.cards.renderers

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardColumnElement
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.models.type
import com.cometchat.cards.theme.CometChatCardThemeResolver

class ColumnElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardColumnElement
        val mode = renderContext.effectiveThemeMode
        val density = context.resources.displayMetrics.density
        val gap = el.gap ?: 8

        if (renderContext.depth >= CometChatCardRenderContext.MAX_DEPTH) {
            renderContext.logger.warning("Max nesting depth exceeded, id=${el.id}")
            return View(context)
        }

        // Column uses WRAP_CONTENT. Parent (Row or card body) controls width.
        val column = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            applyLayoutBackground(this, el.backgroundColor, el.borderRadius, el.borderColor, el.borderWidth, mode, density)
            applyPadding(this, el.padding)
        }

        for ((index, child) in el.items.withIndex()) {
            val renderer = renderContext.registry.getRenderer(child.type)
            if (renderer != null) {
                try {
                    val childView = renderer.renderView(context, child, renderContext.withDepth(renderContext.depth + 1))
                    if (index > 0) {
                        val lp = childView.layoutParams as? LinearLayout.LayoutParams
                            ?: LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        lp.topMargin = (gap * density).toInt()
                        childView.layoutParams = lp
                    }
                    column.addView(childView)
                } catch (e: Exception) {
                    renderContext.logger.error("Renderer exception for ${child.type}: ${e.message}")
                }
            } else {
                renderContext.logger.warning("Unknown element type: ${child.type}, id: ${child.id}")
            }
        }

        return column
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardColumnElement
        val mode = renderContext.effectiveThemeMode
        val gap = el.gap ?: 8
        val borderRadius = el.borderRadius ?: 0
        val shape = RoundedCornerShape(borderRadius.dp)

        if (renderContext.depth >= CometChatCardRenderContext.MAX_DEPTH) {
            renderContext.logger.warning("Max nesting depth exceeded, id=${el.id}")
            return
        }

        // Column align is handled by individual text elements' own align property
        // Column's align in the schema doesn't control child horizontal alignment

        // Column uses wrap content. Parent (Row or card body) controls width.
        // Modifier order: clip → background → border → padding (inside visual boundary)
        var modifier = composePadding(null)
        if (borderRadius > 0) modifier = modifier.clip(shape)
        CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode)?.let { modifier = modifier.background(parseComposeColor(it), shape) }
        val borderColor = CometChatCardThemeResolver.resolveColor(el.borderColor, mode)
        if (borderColor != null && el.borderWidth != null) modifier = modifier.border(el.borderWidth.dp, parseComposeColor(borderColor), shape)
        modifier = modifier.then(composePadding(el.padding))

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(gap.dp),
            horizontalAlignment = Alignment.Start
        ) {
            for (child in el.items) {
                val renderer = renderContext.registry.getRenderer(child.type)
                if (renderer != null) {
                    renderer.RenderComposable(child, renderContext.withDepth(renderContext.depth + 1))
                } else {
                    renderContext.logger.warning("Unknown element type: ${child.type}, id: ${child.id}")
                }
            }
        }
    }
}
