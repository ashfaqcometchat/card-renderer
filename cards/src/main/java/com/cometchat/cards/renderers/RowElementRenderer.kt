package com.cometchat.cards.renderers

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.models.CometChatCardRowElement
import com.cometchat.cards.models.type
import com.cometchat.cards.theme.CometChatCardThemeResolver

class RowElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardRowElement
        val mode = renderContext.effectiveThemeMode
        val density = context.resources.displayMetrics.density
        val gap = el.gap ?: 8

        if (renderContext.depth >= CometChatCardRenderContext.MAX_DEPTH) {
            renderContext.logger.warning("Max nesting depth exceeded, id=${el.id}")
            return View(context)
        }

        val row = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            applyLayoutBackground(this, el.backgroundColor, el.borderRadius, el.borderColor, el.borderWidth, mode, density)
            applyPadding(this, el.padding)
        }

        for ((index, child) in el.items.withIndex()) {
            val renderer = renderContext.registry.getRenderer(child.type)
            if (renderer != null) {
                try {
                    val childView = renderer.renderView(context, child, renderContext.withDepth(renderContext.depth + 1))
                    if (index > 0) {
                        val lp = childView.layoutParams as? ViewGroup.MarginLayoutParams
                        if (lp != null) {
                            lp.marginStart = (gap * density).toInt()
                        } else {
                            childView.layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                            ).apply { marginStart = (gap * density).toInt() }
                        }
                    }
                    row.addView(childView)
                } catch (e: Exception) {
                    renderContext.logger.error("Renderer exception for ${child.type}: ${e.message}")
                }
            } else {
                renderContext.logger.warning("Unknown element type: ${child.type}, id: ${child.id}")
            }
        }

        return if (el.scrollable == true) {
            HorizontalScrollView(context).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                addView(row)
            }
        } else row
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardRowElement
        val mode = renderContext.effectiveThemeMode
        val gap = el.gap ?: 8
        val borderRadius = el.borderRadius ?: 0
        val shape = RoundedCornerShape(borderRadius.dp)

        if (renderContext.depth >= CometChatCardRenderContext.MAX_DEPTH) {
            renderContext.logger.warning("Max nesting depth exceeded, id=${el.id}")
            return
        }

        var modifier = composePadding(el.padding).fillMaxWidth()
        if (borderRadius > 0) modifier = modifier.clip(shape)
        val bgColor = CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode)
        bgColor?.let { modifier = modifier.background(parseComposeColor(it), shape) }
        val borderColor = CometChatCardThemeResolver.resolveColor(el.borderColor, mode)
        if (borderColor != null && el.borderWidth != null) {
            modifier = modifier.border(el.borderWidth.dp, parseComposeColor(borderColor), shape)
        }
        if (el.scrollable == true) modifier = modifier.horizontalScroll(rememberScrollState())

        val horizontalArrangement = when (el.align) {
            "center" -> Arrangement.Center
            "end" -> Arrangement.End
            "spaceBetween" -> Arrangement.SpaceBetween
            "spaceAround" -> Arrangement.SpaceAround
            else -> Arrangement.Start
        }.let { base ->
            if (gap > 0 && el.align != "spaceBetween" && el.align != "spaceAround") {
                Arrangement.spacedBy(gap.dp, when (el.align) {
                    "center" -> Alignment.CenterHorizontally
                    "end" -> Alignment.End
                    else -> Alignment.Start
                })
            } else base
        }

        val verticalAlignment = when (el.crossAlign) {
            "center" -> Alignment.CenterVertically
            "end" -> Alignment.Bottom
            else -> Alignment.Top
        }

        if (el.wrap == true) {
            FlowRow(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(gap.dp),
                verticalArrangement = Arrangement.spacedBy(gap.dp)
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
        } else {
            Row(
                modifier = modifier,
                horizontalArrangement = horizontalArrangement,
                verticalAlignment = verticalAlignment
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
}

internal fun applyLayoutBackground(
    view: View, backgroundColor: com.cometchat.cards.models.CometChatCardColorOrHex?,
    borderRadius: Int?, borderColor: com.cometchat.cards.models.CometChatCardColorOrHex?,
    borderWidth: Int?, mode: com.cometchat.cards.models.CometChatCardThemeMode, density: Float
) {
    val bgHex = CometChatCardThemeResolver.resolveColor(backgroundColor, mode)
    val borderHex = CometChatCardThemeResolver.resolveColor(borderColor, mode)
    if (bgHex != null || borderHex != null) {
        view.background = GradientDrawable().apply {
            cornerRadius = (borderRadius ?: 0) * density
            bgHex?.let { runCatching { setColor(Color.parseColor(it)) } }
            if (borderHex != null && borderWidth != null) {
                runCatching { setStroke((borderWidth * density).toInt(), Color.parseColor(borderHex)) }
            }
        }
    }
}
