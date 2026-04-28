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
import com.cometchat.cards.models.CometChatCardButtonElement
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.models.CometChatCardRowElement
import com.cometchat.cards.models.type
import com.cometchat.cards.theme.CometChatCardThemeResolver

/**
 * Row layout strategy (consistent for View and Compose):
 *
 * 1. Children that are layout containers (column, row, grid) get equal weight
 *    so they share the row width evenly — matching CSS flexbox behavior where
 *    block-level flex children expand to fill available space.
 *
 * 2. Children that are content elements (text, badge, button, icon, etc.)
 *    keep their natural/intrinsic width.
 *
 * 3. Buttons with fullWidth=true get weight(1f) to fill remaining space.
 *
 * 4. spaceBetween/spaceAround: Compose uses Arrangement natively.
 *    View system inserts invisible weighted spacer views between children.
 *
 * 5. gap: applied as marginStart on non-first children (View) or
 *    Arrangement.spacedBy (Compose, for non-spaceBetween/spaceAround).
 */
class RowElementRenderer : CometChatCardElementRenderer {

    private val layoutTypes = setOf("column", "row", "grid")

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardRowElement
        val mode = renderContext.effectiveThemeMode
        val density = context.resources.displayMetrics.density
        val gap = el.gap ?: 8
        val isSpaced = el.align == "spaceBetween" || el.align == "spaceAround"

        if (renderContext.depth >= CometChatCardRenderContext.MAX_DEPTH) {
            renderContext.logger.warning("Max nesting depth exceeded, id=${el.id}")
            return View(context)
        }

        val row = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            // crossAlign maps to vertical gravity
            gravity = when (el.crossAlign) {
                "center" -> android.view.Gravity.CENTER_VERTICAL
                "end" -> android.view.Gravity.BOTTOM
                else -> android.view.Gravity.TOP
            }
            applyLayoutBackground(this, el.backgroundColor, el.borderRadius, el.borderColor, el.borderWidth, mode, density)
            applyPadding(this, el.padding)
        }

        // Count layout children to determine if we need weight distribution
        val layoutChildCount = el.items.count { it.type in layoutTypes }
        val hasLayoutChildren = layoutChildCount > 0

        for ((index, child) in el.items.withIndex()) {
            val renderer = renderContext.registry.getRenderer(child.type)
            if (renderer != null) {
                try {
                    val childView = renderer.renderView(context, child, renderContext.withDepth(renderContext.depth + 1))
                    val isLayoutChild = child.type in layoutTypes
                    val isFullWidthButton = (child as? CometChatCardButtonElement)?.fullWidth == true

                    val lp: LinearLayout.LayoutParams = when {
                        // Layout children (column, row, grid) get equal weight to share space
                        isLayoutChild -> LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
                        // fullWidth buttons get weight to fill remaining space
                        isFullWidthButton -> LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
                        // Content children keep natural width
                        else -> LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    }

                    // Apply gap as marginStart on non-first children
                    if (index > 0 && gap > 0 && !isSpaced) {
                        lp.marginStart = (gap * density).toInt()
                    }

                    childView.layoutParams = lp
                    row.addView(childView)
                } catch (e: Exception) {
                    renderContext.logger.error("Renderer exception for ${child.type}: ${e.message}")
                }
            } else {
                renderContext.logger.warning("Unknown element type: ${child.type}, id: ${child.id}")
            }
        }

        // For spaceBetween/spaceAround without layout children, insert weighted spacers
        // (When layout children exist, they already have weight=1f which distributes space)
        if (isSpaced && !hasLayoutChildren) {
            val childCount = row.childCount
            if (childCount > 1) {
                val insertions = mutableListOf<Int>()
                if (el.align == "spaceAround") insertions.add(0)
                for (i in 0 until childCount - 1) {
                    insertions.add(if (el.align == "spaceAround") (i * 2 + 2) else (i * 2 + 1))
                }
                if (el.align == "spaceAround") insertions.add(insertions.size + childCount)

                for (pos in insertions.reversed()) {
                    val spacer = View(context).apply {
                        layoutParams = LinearLayout.LayoutParams(0, 1, 1f)
                    }
                    row.addView(spacer, pos.coerceAtMost(row.childCount))
                }
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

        var modifier = Modifier.fillMaxWidth()
        if (borderRadius > 0) modifier = modifier.clip(shape)
        val bgColor = CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode)
        bgColor?.let { modifier = modifier.background(parseComposeColor(it), shape) }
        val borderColor = CometChatCardThemeResolver.resolveColor(el.borderColor, mode)
        if (borderColor != null && el.borderWidth != null) {
            modifier = modifier.border(el.borderWidth.dp, parseComposeColor(borderColor), shape)
        }
        modifier = modifier.then(composePadding(el.padding))
        if (el.scrollable == true) modifier = modifier.horizontalScroll(rememberScrollState())

        val isSpaced = el.align == "spaceBetween" || el.align == "spaceAround"

        val horizontalArrangement: Arrangement.Horizontal = when (el.align) {
            "center" -> if (gap > 0) Arrangement.spacedBy(gap.dp, Alignment.CenterHorizontally) else Arrangement.Center
            "end" -> if (gap > 0) Arrangement.spacedBy(gap.dp, Alignment.End) else Arrangement.End
            "spaceBetween" -> Arrangement.SpaceBetween
            "spaceAround" -> Arrangement.SpaceAround
            else -> if (gap > 0) Arrangement.spacedBy(gap.dp) else Arrangement.Start
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
                        val isLayoutChild = child.type in layoutTypes
                        val isFullWidthButton = (child as? CometChatCardButtonElement)?.fullWidth == true

                        // Layout children and fullWidth buttons get weight(1f) to share space
                        if (isLayoutChild || isFullWidthButton) {
                            Box(modifier = Modifier.weight(1f)) {
                                renderer.RenderComposable(child, renderContext.withDepth(renderContext.depth + 1))
                            }
                        } else {
                            renderer.RenderComposable(child, renderContext.withDepth(renderContext.depth + 1))
                        }
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
