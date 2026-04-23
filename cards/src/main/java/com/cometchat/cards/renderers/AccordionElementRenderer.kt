package com.cometchat.cards.renderers

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.*
import com.cometchat.cards.theme.CometChatCardThemeResolver

class AccordionElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardAccordionElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val density = context.resources.displayMetrics.density
        val borderRadius = el.borderRadius ?: 0
        var isExpanded = el.expandedByDefault ?: false

        if (renderContext.depth >= CometChatCardRenderContext.MAX_DEPTH) {
            renderContext.logger.warning("Max nesting depth exceeded, id=${el.id}")
            return View(context)
        }

        val container = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            if (el.border == true) {
                val borderColor = CometChatCardThemeResolver.resolveColor(null, mode, theme.accordionBorderColor)
                background = GradientDrawable().apply {
                    cornerRadius = borderRadius * density
                    borderColor?.let { runCatching { setStroke((1 * density).toInt(), Color.parseColor(it)) } }
                }
            }
            applyPadding(this, el.padding)
        }

        // Header
        val headerView: View = when (val header = el.header) {
            is CometChatCardAccordionHeader.Text -> {
                TextView(context).apply {
                    text = header.value
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, (el.fontSize ?: 16).toFloat())
                    typeface = if (el.fontWeight == "bold") Typeface.DEFAULT_BOLD else Typeface.DEFAULT
                    val headerBg = CometChatCardThemeResolver.resolveColor(null, mode, theme.accordionHeaderBg)
                    headerBg?.let { runCatching { setBackgroundColor(Color.parseColor(it)) } }
                    setPadding((8 * density).toInt(), (8 * density).toInt(), (8 * density).toInt(), (8 * density).toInt())
                }
            }
            is CometChatCardAccordionHeader.Elements -> {
                val row = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL }
                for (child in header.items) {
                    val renderer = renderContext.registry.getRenderer(child.type)
                    renderer?.let {
                        try { row.addView(it.renderView(context, child, renderContext.withDepth(renderContext.depth + 1))) }
                        catch (_: Exception) {}
                    }
                }
                row
            }
        }

        // Body container
        val bodyContainer = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            visibility = if (isExpanded) View.VISIBLE else View.GONE
        }

        for (child in el.body) {
            val renderer = renderContext.registry.getRenderer(child.type)
            if (renderer != null) {
                try { bodyContainer.addView(renderer.renderView(context, child, renderContext.withDepth(renderContext.depth + 1))) }
                catch (e: Exception) { renderContext.logger.error("Renderer exception: ${e.message}") }
            }
        }

        headerView.setOnClickListener {
            isExpanded = !isExpanded
            bodyContainer.visibility = if (isExpanded) View.VISIBLE else View.GONE
        }

        container.addView(headerView)
        container.addView(bodyContainer)
        return container
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardAccordionElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val borderRadius = el.borderRadius ?: 0
        val shape = RoundedCornerShape(borderRadius.dp)
        var isExpanded by remember { mutableStateOf(el.expandedByDefault ?: false) }

        if (renderContext.depth >= CometChatCardRenderContext.MAX_DEPTH) {
            renderContext.logger.warning("Max nesting depth exceeded, id=${el.id}")
            return
        }

        var modifier = composePadding(el.padding).fillMaxWidth()
        if (borderRadius > 0) modifier = modifier.clip(shape)
        if (el.border == true) {
            val borderColor = CometChatCardThemeResolver.resolveColor(null, mode, theme.accordionBorderColor)
            borderColor?.let { modifier = modifier.border(1.dp, parseComposeColor(it), shape) }
        }

        Column(modifier = modifier.semantics { stateDescription = if (isExpanded) "Expanded" else "Collapsed" }) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .then(
                        CometChatCardThemeResolver.resolveColor(null, mode, theme.accordionHeaderBg)
                            ?.let { Modifier.background(parseComposeColor(it)) } ?: Modifier
                    )
                    .padding(8.dp)
            ) {
                when (val header = el.header) {
                    is CometChatCardAccordionHeader.Text -> {
                        Text(
                            text = header.value,
                            fontSize = (el.fontSize ?: 16).sp,
                            fontWeight = if (el.fontWeight == "bold") FontWeight.Bold else FontWeight.Normal
                        )
                    }
                    is CometChatCardAccordionHeader.Elements -> {
                        Row {
                            for (child in header.items) {
                                val renderer = renderContext.registry.getRenderer(child.type)
                                renderer?.let {
                                    try { it.RenderComposable(child, renderContext.withDepth(renderContext.depth + 1)) }
                                    catch (_: Exception) {}
                                }
                            }
                        }
                    }
                }
            }

            // Body
            AnimatedVisibility(visible = isExpanded, enter = expandVertically(), exit = shrinkVertically()) {
                Column {
                    for (child in el.body) {
                        val renderer = renderContext.registry.getRenderer(child.type)
                        if (renderer != null) {
                            try { renderer.RenderComposable(child, renderContext.withDepth(renderContext.depth + 1)) }
                            catch (e: Exception) { renderContext.logger.error("Renderer exception: ${e.message}") }
                        }
                    }
                }
            }
        }
    }
}
